function getFormDataEditPost(){

    const inputTitle=document.getElementById("title");
    const date = new Date();
    const dateString=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes();
    const input = document.querySelector("input[type=file]");
    const inputImage = document.getElementById("inputImage");
    const postData = {
        id:post.id,
        title:inputTitle.value,
        content: data.getData(),
        statusInt: 0,
        imagePost:inputImage.value,
        createdAt: dateString,
        userId:post.userId
    };
    console.log(postData);
    console.log(input.files[0]);

    const formData = new FormData();
    formData.append("post",new Blob([JSON.stringify(postData)], {type: 'application/json'}) );
    formData.append("file",input.files[0]??null);

     edit(formData);

}
function edit(formData){
    fetch(`http://localhost:8080/api/edit-post`, {
        method: "post",
        body:formData
    })
        .then(res => res.json())
        .then(response => {
            console.log(response.message);
            showMessage(response.message)
            if (response.status === "00") {
                setTimeout(() => {
                    window.open("http://localhost:8080/profile/home");
                },1500)
            }
        })
}