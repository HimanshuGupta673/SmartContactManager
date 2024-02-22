const toggleSideBar = () => {
    const $sidebar = $(".sidebar");
    if ($sidebar.is(":visible")) {
        $sidebar.slideUp();
        $(".content").animate({ marginLeft: "4%" });
    } else {
        $sidebar.slideDown();
        $(".content").animate({ marginLeft: "20%" });
    }
}
const search = () => {
    let query = $("#search-input").val();
    if (query == '') {
        $(".search-result").hide();
    } else {
        let url = `http://localhost:8081/search/${query}`;
        fetch(url)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                let text = `<div class='list-group'>`;

                data.forEach((contact) => {
                    text += `<a href="/user/${contact.cId}/contact" class="list-group-item list-group-item-action">${contact.name}</a>`;
                });

                text += `</div>`;
                $(".search-result").html(text);
                $(".search-result").show();
            });
    }
};
