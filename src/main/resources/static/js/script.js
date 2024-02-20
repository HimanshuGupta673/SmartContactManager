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
