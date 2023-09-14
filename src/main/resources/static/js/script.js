 document.addEventListener('DOMContentLoaded', function() {
  var messageInput = document.getElementById('message-input');
  var sendButton = document.getElementById('send-button');
  var chatMessages = document.getElementById('chat-messages');

  // Aggiungi un ascoltatore di eventi al pulsante di invio
  sendButton.addEventListener('click', function() {
    var message = messageInput.value;
    if (message.trim() !== '') {
      addChatMessage('user', message); // Aggiungi il messaggio utente alla chat
      messageInput.value = ''; // Resetta l'input del messaggio
      sendMessageToAI(message); // Invia il messaggio all'IA per la risposta
    }
  });

  // Funzione per aggiungere un messaggio alla chat
  function addChatMessage(role, content) {
    var messageElement = document.createElement('div');
    messageElement.className = 'message ' + role;
    messageElement.innerHTML = '<p>' + content + '</p>';
    chatMessages.appendChild(messageElement);
    chatMessages.scrollTop = chatMessages.scrollHeight;
  }

  // Funzione per inviare il messaggio all'IA e ricevere la risposta
  function sendMessageToAI(message) {
    // Effettua una chiamata AJAX all'endpoint appropriato
    // per ottenere la risposta dall'IA
    // Inserisci qui il tuo codice per la chiamata AJAX
    // Quando ricevi la risposta, aggiungi il messaggio IA alla chat
    var response = 'This is the AI response'; // S
