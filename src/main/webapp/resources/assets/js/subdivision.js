$(document).ready(function (e) {
    /**
     * Перенаправление на страницу добавления подраздела
     */
    $(document).on("click", ".js-go-to-add-subdivision", function (e) {
        $checkedInput = $('input[type=checkbox]:checked');

        if($checkedInput.length > 0) {

            typeItem = $checkedInput.attr("data-type");
            idItem = $checkedInput.attr("data-id");

            location.href = window.location + "addFormSubdivision/" + typeItem + "/" + idItem;
        } else {
            alert("Необходимо выбрать офис/подразделение");
        }
    });


    $(document).on("click", ".js-add-subdivision", function(e) {
        e.preventDefault();

        //Удаляем ранее отображаемые ошибки
        $("span.error").remove();

        $.post({
            url: '/addSubdivision',
            data: $('form[name=subdivision]').serialize(),
            success: function (res) {

                if (res.validated) {
                    location.href = "/";
                } else {
                    //Показать ошибки валидации
                    $.each(res.errorMessages, function (key, value) {
                        $('input[name=' + key + ']').after('<span class="error">' + value + '</span>');
                    });
                }
            }
        });
    });
});