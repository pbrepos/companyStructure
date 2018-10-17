$(document).ready(function (e) {

    /**
     * Перенаправление на страницу добавления сотрудника
     */
    $(document).on("click", ".js-go-to-add-employee", function (e) {
        $checkedInput = $('input[type=checkbox]:checked');

        if (!$(this).hasClass("block-btn")) {

            if ($checkedInput.length > 0 && $checkedInput.data("type") === "subdivision") {

                idItem = $checkedInput.attr("data-id");

                location.href = window.location + "employee/" + idItem;
            } else {
                alert("Необходимо выбрать подразделение");
            }
        }
    });

    /**
     * Добавления сотрудника
     */

    $(document).on("click", ".js-add-employee", function (e) {
        e.preventDefault();

        //Удаляем ранее отображаемые ошибки
        $("span.error").remove();

        $.post({
            url: '/addEmployee',
            data: $('form[name=employee]').serialize(),
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

    /**
     * Редактирование сотрудника
     */
    $(document).on("click", ".js-edit-employee", function (e) {
        e.preventDefault();



        $.post({
            url: "/employeeEdit",
            data: $('form[name=employeeEdit]').serialize(),
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


    /**
     * Удаление сотрудника
     */
    $(document).on("click", ".js-employee-remove", function (e) {
        var id = +$(this).data("id");

        $.post({
            url: '/removeEmployee',
            data: {id: id},
            success: function (response) {
                $(".js-employee-remove[data-id='" + id + "']").closest("tr").remove();
                alert(response);
            }
        });
    })
});