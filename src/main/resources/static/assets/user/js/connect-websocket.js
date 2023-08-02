const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/websocket'
});
connect();

stompClient.onConnect = (frame) => {

    console.log('Connected: ' + frame);

    stompClient.subscribe('/topic/notification', (notify) => {
        // notification(JSON.parse(notify.body).data.message)
    });

    stompClient.subscribe('/user/queue/notification', (notify) => {
        let notifyJson=JSON.parse(notify.body);
        notification(notifyJson.data.message,notifyJson)
    });
};
stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};
function connect() {
    stompClient.activate();
}
function sendNotification(postId) {
    stompClient.publish({
        destination: "/app/notification",
        body: JSON.stringify(postId)
    });
}



