const API_URL = 'http://' + window.location.hostname + ':8080/api';
const WS_URL = 'http://' + window.location.hostname + ':8080/ws';

let stompClient = null;
let currentUser = null;
let currentRoom = null;

// --- UTILS ---
function showScreen(screenId) {
    document.querySelectorAll('.screen').forEach(s => s.classList.remove('active'));
    document.getElementById(screenId).classList.add('active');
}

// --- AUTH & NAVIGATION ---
async function login() {
    const username = document.getElementById('username-input').value.trim();
    if (!username) return alert("Please enter a username");

    try {
        const response = await fetch(`${API_URL}/login?username=${username}`, { method: 'POST' });
        currentUser = await response.json();

        document.getElementById('welcome-msg').innerText = `Welcome, ${currentUser.username}`;

        if (currentUser.admin) {
            document.getElementById('admin-panel').style.display = 'block';
            loadAdminRequests();
        } else {
            document.getElementById('admin-panel').style.display = 'none';
        }

        showScreen('dashboard-screen');
        loadMyRequests(); // Start polling or loading requests
    } catch (error) {
        alert("Login failed: " + error);
    }
}

function logout() {
    currentUser = null;
    if (stompClient) stompClient.disconnect();
    window.location.reload();
}

// --- JOIN REQUESTS ---
async function requestJoin() {
    const room = document.getElementById('room-input').value.trim();
    if (!room) return alert("Enter room name");

    const response = await fetch(`${API_URL}/join-request?username=${currentUser.username}&room=${room}`, { method: 'POST' });
    const request = await response.json();

    if (request.status === 'APPROVED') {
        enterRoom(room);
    } else {
        alert(`Request sent! Status: ${request.status}`);
        loadMyRequests();
    }
}

// Poll for request status updates roughly every few seconds if user is waiting? 
// For simplicity, we just have them refresh or re-request.
async function loadMyRequests() {
    // In a real app we'd fetch list. 
    // Here we'll just show the one they typed if we want, or simplicity: 
    // The user has to click "Request" again to check status essentially, 
    // OR we can't easily list *all* their requests without a new API endpoint.
    // Let's add that endpoint quickly or just leave it manual.
    // Simpler: Just rely on "Request" button acting as "Check/Join" button.
}

// Override requestJoin to handle "Check Status"
async function checkJoin(roomName) {
    const response = await fetch(`${API_URL}/request-status?username=${currentUser.username}&room=${roomName}`);
    const request = await response.json();
    if (request && request.status === 'APPROVED') {
        enterRoom(roomName);
    } else if (request) {
        alert(`Status for ${roomName}: ${request.status}`);
    } else {
        alert("No request found. Send one first.");
    }
}


// --- ADMIN ---
async function loadAdminRequests() {
    if (!currentUser.admin) return;
    const response = await fetch(`${API_URL}/admin/requests`);
    const requests = await response.json();

    const list = document.getElementById('admin-requests-list');
    list.innerHTML = '';
    requests.forEach(req => {
        const li = document.createElement('li');
        li.className = 'list-item';
        li.innerHTML = `
            <span><b>${req.username}</b> -> ${req.roomName}</span>
            <div>
                <button class="btn-small" style="background:var(--success-color)" onclick="handleRequest('${req.id}', 'APPROVED')">✔</button>
                <button class="btn-small" style="background:var(--danger-color)" onclick="handleRequest('${req.id}', 'REJECTED')">✘</button>
            </div>
        `;
        list.appendChild(li);
    });
}

async function handleRequest(id, status) {
    await fetch(`${API_URL}/admin/handle?requestId=${id}&status=${status}`, { method: 'POST' });
    loadAdminRequests();
}

// --- CHAT ROOM ---
function enterRoom(roomId) {
    currentRoom = roomId;
    document.getElementById('current-room-name').innerText = roomId;
    document.getElementById('chat-messages').innerHTML = ''; // Clear prev messages
    showScreen('chat-screen');
    connectWebSocket();
    loadHistory(roomId);
}

function leaveRoom() {
    if (stompClient) stompClient.disconnect();
    currentRoom = null;
    showScreen('dashboard-screen');
}

async function loadHistory(roomId) {
    const response = await fetch(`${API_URL}/history/${roomId}`);
    const messages = await response.json();
    messages.forEach(displayMessage);
}

function connectWebSocket() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null; // Disable debug logs for clean console

    stompClient.connect({}, function (frame) {
        // Subscribe to room
        stompClient.subscribe(`/topic/room/${currentRoom}`, function (messageOutput) {
            displayMessage(JSON.parse(messageOutput.body));
        });

        // Notify join
        stompClient.send(`/app/chat/${currentRoom}/addUser`, {}, JSON.stringify({
            sender: currentUser.username,
            type: 'JOIN'
        }));
    });
}

function sendMessage() {
    const content = document.getElementById('message-input').value.trim();
    if (!content || !stompClient) return;

    stompClient.send(`/app/chat/${currentRoom}/sendMessage`, {}, JSON.stringify({
        sender: currentUser.username,
        content: content,
        type: 'CHAT'
    }));
    document.getElementById('message-input').value = '';
}

function displayMessage(message) {
    const box = document.getElementById('chat-messages');

    if (message.type === 'JOIN') {
        const div = document.createElement('div');
        div.style.textAlign = 'center';
        div.style.fontSize = '0.8rem';
        div.style.opacity = '0.6';
        div.style.margin = '10px 0';
        div.innerText = `${message.sender} joined the room`;
        box.appendChild(div);
    } else if (message.type === 'CHAT') {
        const div = document.createElement('div');
        const isMine = message.sender === currentUser.username;
        div.className = `message-bubble ${isMine ? 'mine' : 'theirs'}`;

        div.innerHTML = `
            <span class="message-info">${message.sender}</span>
            ${message.content}
        `;
        box.appendChild(div);
    }

    box.scrollTop = box.scrollHeight; // Auto scroll to bottom
}

// Initialize on load
// (Nothing to do until login)
