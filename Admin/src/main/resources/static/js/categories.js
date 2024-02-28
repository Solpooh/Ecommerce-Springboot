$('document').ready(function () {
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href'); // /admin/findById?id=

        console.log(href);
        // ajax get함수 => 서버에 요청을 보냄
        $.get(href, function (category, status) {
            console.log(category);
            console.log(status);
            $('#idEdit').val(category.id);
            $('#nameEdit').val(category.name);
        });
        $('#editModal').modal();
    });
});