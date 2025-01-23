const WebSocknet = require('ws');

// WebSocket 서버 URL 설정
const url = 'ws://localhost:8080/ws';
const connection = new WebSocket(url);

// WebSocket 이벤트 설정
connection.onopen = () => {
  console.log('WebSocket connection opened.');
  connection.send('Hello WebSocket!');
};

connection.onmessage = (event) => {
  console.log('Received message:', event.data);
};

connection.onclose = () => {
  console.log('WebSocket connection closed.');
};

connection.onerror = (error) => {
  console.log('WebSocket error:', error);
};
