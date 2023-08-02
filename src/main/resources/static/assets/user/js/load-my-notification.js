// loadMyNotificationList()
function loadMyNotificationList() {

    load();

}

function load() {
    fetch(`http://localhost:8080/user/api/notification/get-my-list`, {
        method: "post",
        // body:formData
    })
        .then(res => res.json())
        .then(response => {
            console.log(response.data);

            if (response.status === "00") {
                loadDataNotificationList(response.data.content)
            }
        })
}

function loadDataNotificationList(notificationList) {
    let result = ``;
    for (const item of notificationList) {
        result += `
                            <div class="dropdown-divider"></div>
                            <form method="post" action="/post/detail-post?postId=${item.sourceId}">
                            <button class="border-0 border-white w-100">
                              <a  class="dropdown-item preview-item fw-normal d-flex justify-content-between">
                                <div class="preview-thumbnail">
                                    <div class="preview-icon bg-dark rounded-circle">
                                        <i class="mdi mdi-calendar text-success"></i>
                                    </div>
                                </div>
                                <div class="preview-item-content d-flex flex-column  w-100">
                                    <div class="d-flex justify-content-between">
                                      <p class="preview-subject mb-1 fw-bold">${item.title}</p>
                                      <p class="preview-subject mb-1">${item.createdAt}</p>
                                    </div>
                                    
                                    <p class="align-self-start d-inline-block text-muted fw-normal mb-0" style="font-size: 13px">
                                        ${item.message}
                                    </p>
                                </div>
                            </a>
                            </button>
                           
                            </form>
                           
                            
        `;

    }
    result = result + ` <div class="dropdown-divider"></div>
                        <a onclick="openPopupNotificationList()">
                          <p class="p-3 mb-0 text-center">See all notifications</p>
                        </a>    
                      
`
    let content = document.getElementById("notification-list");
    content.innerHTML = result;
}

function loadMyNotificationListAll() {

    loadAll();

}

function loadAll() {
    fetch(`http://localhost:8080/user/api/notification/get-my-list-all`, {
        method: "post",
        // body:formData
    })
        .then(res => res.json())
        .then(response => {
            console.log(response.data);

            if (response.status === "00") {
                loadDataNotificationListAll(response.data)
            }
        })
}

function openPopupNotificationList() {

    loadMyNotificationListAll();


    const modalE = new bootstrap.Modal(document.getElementById('my-modal-notification'), {
        keyboard: false
    });




    modalE.show();
}
function loadDataNotificationListAll(notificationList) {
    let result = ``;
    for (const item of notificationList) {
        result += `
                            <div class="dropdown-divider"></div>
                            <form method="post" action="/post/detail-post?postId=${item.sourceId}">
                            <button class="border-0 border-white w-100">
                              <a  class="dropdown-item px-2 preview-item fw-normal d-flex justify-content-between">
<!--                                <div class="preview-thumbnail">-->
<!--                                    <div class="preview-icon bg-dark rounded-circle">-->
<!--                                        <i class="mdi mdi-calendar text-success"></i>-->
<!--                                    </div>-->
<!--                                </div>-->
                                <div class="preview-item-content d-flex flex-column  w-100">
                                    <div class="d-flex justify-content-between">
                                      <p class="preview-subject mb-1 fw-bold">${item.title}</p>
                                      <p class="preview-subject mb-1">${item.createdAt}</p>
                                    </div>
                                    
                                    <p class="align-self-start d-inline-block text-muted fw-normal mb-0" style="font-size: 13px">
                                        ${item.message}
                                    </p>
                                </div>
                            </a>
                            </button>
                           
                            </form>
                           
                            
        `;

    }

    // document.getElementsByClassName("single_page_content").item(0).innerHTML = post.content
    let content = document.getElementsByClassName("modal-body").item(0);
    content.innerHTML = result;
}