function success(message) {
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

function error(message) {
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
            background: "linear-gradient(to right, #F50000, #F53D3D)",
        },
        onClick: function(){} // Callback after click
    }).showToast();
}

function notification(message,notifyJson) {
    Toastify({
        text: message,
        duration: 1000000,
        // destination: "https://github.com/apvarun/toastify-js",
        newWindow: true,
        close: true,
        gravity: "bottom", // `top` or `bottom`
        position: "right", // `left`, `center` or `right`
        stopOnFocus: true, // Prevents dismissing of toast on hover
        style: {
            background: "linear-gradient(to right, #07c8f2, #2bd1f5)",
        },
        onClick: toDetailNotification.bind(null,notifyJson)// Callback after click
    }).showToast();
}
