function archivePost(postId){
    fetch(`http://localhost:8080/api/save-archive-post`, {
        method: "post",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(postId)
    })
        .then(res => res.json())
        .then(response => {
            if (response.status === '00') {
                toDetailPost(postId)
            }
        })
}
function changeFollow(post) {
    fetch(`http://localhost:8080/api/follow-user`, {
        method: "post",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(post.userId)
    })
        .then(res => res.json())
        .then(response => {

            if (response.status === '00') {

                toDetailPost(post.id)
            }
        })
}
function toDetailPost(postId) {
    let form=document.createElement("form");
    form.setAttribute("method", "post");

    form.setAttribute("action", `/post/detail-post?postId=${postId}`);
    form.setAttribute("target", "view");
    document.body.appendChild(form);
    window.open('', 'view');
    form.submit();
}

