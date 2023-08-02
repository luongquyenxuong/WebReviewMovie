let page = 1;
let totalPage;
searchPostByConditionTime(1)

function searchPostByConditionTime(page) {

    apiSearchSideUser(getCondition(), page);

}


function apiSearchSideUser(condition, page) {
    let load = document.getElementById("loader");
    load.style.display = "inline-block";
    let contentE = document.getElementById("content-post");
    contentE.style.display = "none";
    const paginationE = document.querySelector(".pagination");
    call(`http://localhost:8080/api/search-post-by-condition-time?page=${page - 1}`, 'post', condition)
        .then(response => {

            if (response.status === "00") {
                totalPage = response.data.totalPages;
                paginate(page, paginationE, searchPostByConditionTime);
                hiddenNoListSideUser(paginationE);
                loadDataSideUser(response.data.content, load, contentE);
            }
            if (response.status === "99") {
                error(response.message)

                displayNoList(load, paginationE);
            }
        })
}

function displayNoList(loader, paginationE) {
    let h1 = document.getElementById("no-list");
    h1.style.display = "block";
    paginationE.style.display = "none";
    loader.style.display = "none";

}

function hiddenNoListSideUser(paginationE) {
    let h1 = document.getElementById("no-list");
    h1.style.display = "none";
    paginationE.style.display = "flex";

}

function displayContent(load, content) {
    load.style.display = "none";
    content.style.display = "flex";
}

function loadDataSideUser(postList, load, content) {

    let result = ``;
    for (const item of postList) {
        result += `
                                        <div class="card col-4 p-0" >
                                            <form method="POST" action="/post/detail-post?postId=${item.id}">
                                            <button class="border-0 border-white w-100">
                                             <img class="card-img-bottom" src="${'../uploads/' + item.imagePost}" style="width:100%;height:300px ;object-fit: cover" alt="">
                                                <div class="card-body">
                                                 <h4 class="card-title text-dark">${item.title}</h4>
                                               
                                                    <p class="card-text">${'Author: ' + item.fullNameUser}</p>
                                                    <div class="">
                                                       <p>${'Time: ' + item.createdAt}</p>
                                                    </div>
                                                 </div>
                                            </button>
                                               
                                            
                                            </form>
                                        </div>
        `;
    }

    content.innerHTML = result;

    //
    displayContent(load, content);
}

function getCondition() {

    let condition ;

    const inputAuthor = document.getElementById("author");
    const inputTitle = document.getElementById("title");
    const fromDate = document.getElementById("fromDate");
    const toDate = document.getElementById("toDate");
    const date = new Date();
    const dateCurrentString = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();

    //Neu input date from,date to : khong co gia tri
    //Thi Date From se la 0000-00-00 00:00 //Date To se la current date
    if (fromDate.value === "" && toDate.value === "") {

        condition = {
            author: "%" + inputAuthor.value.trim() + "%",
            status: 1,
            title: "%" + inputTitle.value.trim() + "%",
            fromDate: "0000-00-00 00:00",
            toDate: dateCurrentString
        }

        return condition;
    }

    //Neu input Date From : khong co gia tri
    //Date From se la 0000-00-00 00:00
    if(fromDate.value===""){

        condition = {
            author: "%" + inputAuthor.value.trim() + "%",
            status: 1,
            title: "%" + inputTitle.value.trim() + "%",
            fromDate:  "0000-00-00 00:00",
            toDate: toDate.value + " 23:59"
        }

        return condition;
    }

    //Neu input date to : khong co gia tri
    //Date To se la current date
    if(toDate.value===""){

        condition = {
            author: "%" + inputAuthor.value.trim() + "%",
            status: 1,
            title: "%" + inputTitle.value.trim() + "%",
            fromDate:  fromDate.value + " 00:00",
            toDate:dateCurrentString
        }

        return condition;
    }

    //Con khong thi gan nhu binh thuong
    condition = {
        author: "%" + inputAuthor.value.trim() + "%",
        status: 1,
        title: "%" + inputTitle.value.trim() + "%",
        fromDate: fromDate.value + " 00:00",
        toDate: toDate.value + " 23:59"
    }

    return condition;

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


















































