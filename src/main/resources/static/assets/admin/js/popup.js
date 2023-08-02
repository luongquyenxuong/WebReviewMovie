function openPopupPost(post) {
    const modalE = new bootstrap.Modal(document.getElementById('myModal'), {
        keyboard: false
    });
    const btn = document.getElementById("btn-post")
    const h4 = document.getElementsByClassName("modal-title")
    h4.item(0).innerText = post.title;

    if (post.statusInt === 1) {
         btn.classList.remove("btn-outline-primary");
        btn.classList.add("btn-outline-warning");
        btn.innerText = "Unconfirm";
        btn.addEventListener("click", () => {
            handleUnConfirmInPopUp(post.id);
        })
    } else {
        btn.classList.remove("btn-outline-warning");
        btn.classList.add("btn-outline-primary");
        btn.innerText = "Confirm";
        btn.addEventListener("click", () => {
            handleConfirmInPopUp(post.id);
        })
    }

    document.getElementsByClassName("single_page_content").item(0).innerHTML = post.content

    modalE.show();
}

function handleConfirmInPopUp(id) {
    call(`http://localhost:8080/admin/api/post/confirm?post-id=${id}`, 'post', {})
        .then(handleResponse)
}
function handleUnConfirmInPopUp(id) {
    call(`http://localhost:8080/admin/api/post/unconfirm?post-id=${id}`, 'post', {})
        .then(handleResponse)
}