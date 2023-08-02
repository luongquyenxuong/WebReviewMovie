let page = 1;
let totalPage;
searchPostByCondition(1);

function searchPostByCondition(page) {

    const inputAuthor = document.getElementById("author")
    const inputTitle = document.getElementById("title")
    const selectStatus = document.getElementById("selectStatus")
    const condition = {
        author: "%"+inputAuthor.value.trim()+"%",
        title: "%"+inputTitle.value.trim()+"%",
        status: selectStatus.value,
    }
    apiSearchSideAdmin(condition, page);
}


function apiSearchSideAdmin(condition, page) {
    let loader = document.getElementById("loader");
    loader.style.display = "inline-block";
    let table = document.getElementById("table");
    table.style.display = "none";
    const paginationE = document.querySelector(".pagination");
    call(`http://localhost:8080/api/search-post-by-condition?page=${page - 1}`, 'post', condition)
        .then(response => {
            if (response.status === "00") {
                totalPage = response.data.totalPages;
                paginate(page,paginationE,searchPostByCondition);
                hiddenNoList(paginationE)
                loadDataSideAdmin(response.data.content, loader, table)
            }
            if (response.status === "99") {
                error(response.message)
                displayNoList(loader,paginationE);

            }
        })
}
function displayNoList(loader,paginationE){
   let h1= document.getElementById("no-list");
    h1.style.display="block";
    paginationE.style.display="none";
    loader.style.display = "none";

}
function hiddenNoList(paginationE){
    let h1= document.getElementById("no-list");
    h1.style.display="none";
    paginationE.style.display="flex";

}

function displayTable(loader,table){
    loader.style.display = "none";
    table.style.display = "table";
}
function loadDataSideAdmin(postList, loader, table) {

    let result = ``;
    let count=0;
    for (const item of postList) {
        result += `<tr>
                                            <td>${++count}</td>
                                            <td>${item.fullNameUser}</td>
                                            <td>${item.title}</td>
                                            <td>${item.createdAt}</td>
                                            <td>${item.status}</td>
                                            <td>
                                                <div class="btn-group" role="group"
                                                     aria-label="Basic mixed styles example">
                                                    <button id="${'btn-view' + item.id}" class="btn btn-outline-info btn-fw">
                                                    View post
                                                    </button>
                                                    `;
        if (item.statusInt) {
            result += ` 
                                                    <button id="${'btn-unconfirm' + item.id}" class="btn btn-outline-warning btn-fw">
                                                        Unconfirm
                                                    </button> `
        } else {
            result += ` 
                                                    <button id="${'btn-confirm' + item.id}" class="btn btn-outline-primary btn-fw">
                                                        Confirm
                                                    </button>`
        }
        result += `                             
                                                    <button id="${'btn-remove' + item.id}" class="btn btn-outline-danger btn-fw">
                                                        Remove
                                                    </button>
                                                 
                                                </div>
                                            </td>
                                        </tr>`;


    }
    document.getElementById("tbody").innerHTML = result;

    // display table and hidden loading
    displayTable(loader,table)
    for (const item of postList) {

        // handle event user click confirm button

        handleConfirm(item.id)

        // handle event user click un confirm button

        handleUnConfirm(item.id)

        // handle event user click view button
        const viewButton = document.getElementById(`btn-view${item.id}`);
        viewButton.addEventListener("click", () => {
            openPopupPost(item)
        })

        handleRemove(item.id)

    }
}

handleResponse = (response) => {
    if (response.status === '00') {
        success(response.message);
        // reload => call search
        searchPostByCondition(page);
    } else {
        error(response.message);
    }
}

function call(url, method, body) {
    return fetch(url, {

        method: method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
        .then(res => res.json());

}

function handleConfirm(id) {
    const confirmButton = document.getElementById(`btn-confirm${id}`);
    if (confirmButton) {
        confirmButton.addEventListener('click', () => {
            call(`http://localhost:8080/admin/api/post/confirm?post-id=${id}`, 'post', {})
                .then(handleResponse)
        });
    }
}

function handleUnConfirm(id,page) {
    const unConfirmButton = document.getElementById(`btn-unconfirm${id}`);
    if (unConfirmButton) {
        unConfirmButton.addEventListener('click', () => {
            call(`http://localhost:8080/admin/api/post/unconfirm?post-id=${id}`, 'post', {})
                .then(handleResponse)
        });
    }
}

function handleRemove(id,page) {
    const removeButton = document.getElementById(`btn-remove${id}`);
    if (removeButton) {
        removeButton.addEventListener('click', () => {
            call(`http://localhost:8080/admin/api/post/remove?post-id=${id}`, 'post', {})
                .then(handleResponse)
        });
    }
}












































