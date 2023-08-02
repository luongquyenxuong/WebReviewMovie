CKEDITOR.replace('editor', {
    height: 400
});

function showMessage(message) {
    Toastify({
        text: message,
        duration: 1500,
        // destination: "https://github.com/apvarun/toastify-js",
        newWindow: true,
        close: true,
        gravity: "top", // `top` or `bottom`
        position: "right", // `left`, `center` or `right`
        stopOnFocus: true, // Prevents dismissing of toast on hover
        style: {
            background: "linear-gradient(to right, #00b09b, #96c93d)",
        },
        onClick: function(){} // Callback after click
    }).showToast();
}

function getFormDataCreatePost(){

    let data=CKEDITOR.instances.editor.getData();
    const inputTitle=document.getElementById("title");
    const date = new Date();
    const dateString=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes();
    const input = document.querySelector("input[type=file]");
    const postData = {
        title:inputTitle.value,
        content: data,
        statusInt: 0,
        createdAt: dateString,
    };
    console.log(postData);

    const formData = new FormData();
    formData.append("post",new Blob([JSON.stringify(postData)], {type: 'application/json'}) );
    formData.append("file",input.files[0]);

     save(formData);

}
function save(formData){
    fetch(`http://localhost:8080/api/create-post`, {
        method: "post",
        body:formData
    })
        .then(res => res.json())
        .then(response => {
            console.log(response.message);
            showMessage(response.message)
            if (response.status === "00") {
                setTimeout(() => {
                    window.location.href="/profile/home";
                },1500)
            }
        })
}
