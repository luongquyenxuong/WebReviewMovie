function toDetailNotification(notifyJson) {
    if (notifyJson.data.typeId===1) {
        let form=document.createElement("form");
        form.setAttribute("method", "post");

        form.setAttribute("action", `/post/detail-post?postId=${notifyJson.data.sourceId}`);
        form.setAttribute("target", "view");
        document.body.appendChild(form);
        window.open('', 'view');
        form.submit();
    }
}
