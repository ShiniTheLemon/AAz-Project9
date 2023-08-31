/**
 * 
 */
 const connect = () => {
    const Stomp = require("stompjs");
    var SockJS = require("sockjs-client");
    SockJS = new SockJS("http://localhost:3000/xxx");
    stompClient = Stomp.over(SockJS);
    stompClient.connect({}, onConnected, onError);
  };
  
  const onConnected = () => {
    console.log("connected");

    stompClient.subscribe(
      "/user/" + 12504 + "/queue/messages",
      onMessageReceived
    );
  };
  
  const sendMessage = (msg) => {
    if (msg.trim() !== "") {
      const message = {
        senderId: 12504,
        recipientId: 12505,
      //  senderName: currentUser.name,
       // recipientName: activeContact.name,
        content: msg,
        timestamp: new Date(),
      };
        
      stompClient.send("/app/chat", {}, JSON.stringify(message));
    }
  };