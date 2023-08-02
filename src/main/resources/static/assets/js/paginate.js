function paginate(page,paginationE,search) {

  const previous =
      '<li class="page-item"><a class="page-link" href="#">Previous</a></li>';
  const next =
      '<li class="page-item"><a class="page-link" href="#">Next</a></li>';
  paginationE.innerHTML = previous;
  for (let i = 0; i < totalPage; i++) {
    if (i + 1 === page) {
      paginationE.innerHTML += `<li class="page-item active"><a class="page-link" href="#">${
          i + 1
      }</a></li>`;
    } else {
      paginationE.innerHTML += `<li class="page-item"><a class="page-link" href="#">${
          i + 1
      }</a></li>`;
    }
  }
  paginationE.innerHTML += next;

  // add event listener
  const listItem = document.querySelectorAll(".page-item");

  let previousItem = listItem[0];

  let nextItem = listItem[totalPage + 1];

  previousItem.addEventListener("click", function (e) {
    onPrevious(page,search);
  });

  nextItem.addEventListener("click", function (e) {
    onNext(page, totalPage,search);
  });

  for (let i = 1; i <= totalPage; i++) {
    listItem[i].addEventListener("click", () => {
      console.log(i);
      onPage(i, page,search);
    });
  }
  if (page == 1) {
    previousItem.classList.add("disabled");
  } else {
    previousItem.classList.remove("disabled");
  }
  if (page === totalPage) {
    nextItem.classList.add("disabled");
  } else {
    nextItem.classList.remove("disabled");
  }
}
function onPrevious(page,search) {
  if (page <= 1) {
    return;
  }
  search(--page);
}
function onNext(page, totalPage,search) {
  if (page >= totalPage) {
    return;
  }
  search(++page);
}

function onPage(pageSelected, page,search) {

  if (pageSelected === page) {
    return;
  }
  search(pageSelected);
}