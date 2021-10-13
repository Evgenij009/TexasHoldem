const chatBtn = document.querySelector(".chat-btn");
const chat = document.querySelector(".fixed-chat-dialog");
const hideDialogBtn = document.querySelector(".dialog-badge");

hideDialogBtn.addEventListener("click", toggleChat);
chatBtn.addEventListener("click", toggleChat);

function toggleChat() {
    chat.classList.toggle("d-none");
    chatBtn.classList.toggle("d-none");
}

const chatUnit = {
    init() {
        this.messagesBody = document.querySelector(".direct-chat-messages");
        this.msgTextArea = document.querySelector(".form-control");
        this.sendBtn = document.querySelector(".send-msg");
        this.form = document.querySelector(".dialog-form");
        this.myName = '';
        this.markMessage = '';
        this.isGivenMyMark = false;
        this.bindEvents();
        this.openSocket();
    },
    initMessage() {
        this.markMessage = '%*#' + Math.random();
        this.sendMessage({
            name: this.name,
            text: this.markMessage
        });
    },
    bindEvents() {
        this.sendBtn.addEventListener("click", (e) => this.send(e));
        this.form.addEventListener("submit", (e) => this.send(e));
    },
    send(e) {
        e.preventDefault();
        if (!checkExistInsertAtackSymbols(this.msgTextArea.value)) {
            this.sendMessage({
                name: this.name,
                text: this.msgTextArea.value
            });
        } else {
            this.msgTextArea.value = "";
        }

        function checkExistInsertAtackSymbols(line) {
            const arraInjectionSymbols = ['{', '}', '$', '<', '>'];
            for (index = 0; index < arraInjectionSymbols.length; index++) {
                if (line.indexOf(arraInjectionSymbols[index]) > -1) {
                    return true;
                }
            }
            return false;
        }
    },
    onOpenSocket() {

    },
    onMessage(msg) {
        let messageLine = msg.text.toString();
        if ((messageLine.indexOf('#') == 2 && messageLine.indexOf('%') == 0 && messageLine.indexOf('*') == 1)) {
            let lineAuth = msg.text.toString();
            if (this.markMessage == lineAuth) {
                this.myName = msg.name;
                this.isGivenMyMark = true;
            }
        } else {
            let isMyMessage = '';
            if (this.myName == msg.name) {
                isMyMessage = ' right';
            }
            this.messagesBody.insertAdjacentHTML('beforeend', `
            <div class="direct-chat-msg${isMyMessage}">
                <div class="direct-chat-info clearfix">
                    <span class="direct-chat-name text-secondary pull-left">${msg.name}</span>
                    <span class="direct-chat-timestamp pull-right">${msg.time}</span></div>
                <img class="direct-chat-img" 
                     src="/images/photo/${msg.img}"
                     alt="img">
                <div class="direct-chat-text">${msg.text}</div>
            </div>
        `);
        }
        if (!this.isGivenMyMark) {
            this.initMessage();
        }
    },
    onClose() {

    },
    sendMessage(msg) {
        this.msgTextArea.value = "";
        this.ws.send(JSON.stringify(msg));
    },
    openSocket() {
        this.ws = new WebSocket(`ws://${location.host}/chat`);
        this.ws.onopen = () => this.onOpenSocket();
        this.ws.onmessage = (e) => this.onMessage(JSON.parse(e.data));
        this.ws.onclose = () => this.onClose();
    }
};

window.addEventListener("load", e => chatUnit.init());

let height = 0;
$('direct-chat-messages direct-chat-msg').each(function (i, value) {
    height += parseInt($(this).height());
});

height += '';

$('direct-chat-messages').animate({scrollTop: height});
