$(document).ready(function (e) {
    /**
     * Редирект на форму создания офиса
     */
    $(".js-redirect-add-office").click(function (e) {
        location.href = "/office";
    })

    /**
     * Выбор только одного checkbox
     */
    $(function () {
        $('.sev_check').click(function (e) {
                $('.sev_check').not(this).prop('checked', false);

                // Отображение информацции офис/подразделение
                if ($(this).is(":checked")) {
                    enabledButton();

                    var type = $(this).data("type");
                    var id = $(this).data("id");
                    var url;
                    var isViewTableEmployes = false; //Флаг отображение таблицы с сотрудниками

                    switch (type) {
                        case 'office':
                            url = "/getOfficeInfo"
                            break;
                        case 'subdivision':
                            url = "/getSubdivisionInfo";
                            isViewTableEmployes = true;
                            break;
                    }

                    $.ajax({
                        url: url,
                        datatype: 'json',
                        type: "post",
                        contentType: "application/json",
                        data: JSON.stringify({
                            id: id
                        }),
                        success: function (response) {
                            // console.log(response);

                            if (response.status === "ok") {
                                if (type === "office") {
                                    $(".container-info").html(template.officeForm(response.data));
                                } else {
                                    $(".container-info").html(template.subdivisionForm(response.data));
                                }
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log(jqXHR.status + ' ' + jqXHR.responseText);
                        }
                    });

                    //Отображение таблицы с сотрудниками

                    if (isViewTableEmployes) {

                        $("input[type='radio']:checked") ? $("input[type='radio']:checked").prop('checked', false) : '';
                        $("input[type='radio'][data-sort='fullName']").prop('checked', true);


                        $.ajax({
                            url: "getEmployeesPage",
                            type: "post",
                            data: {
                                subdivisionId: id,
                            },
                            success: function (response) {
                                // console.log(response);

                                if (response.status === "ok") {

                                    $(".table-employee > tbody").html(template.emoloyeeTableRow(response.data));
                                    $(".container-table-employee").show();

                                    var paginationHtml = "";

                                    for (i = 0; i < response.additionalField; i++) {
                                        var k = i + 1;

                                        paginationHtml += '<li class="js-page-item "  data-page="' + i + '"><a class="page-link" href="javascript:void(0);">' + k + '</a></li>';
                                    }

                                    $paginationUl = $(".container-pagination").find(".pagination-ul");

                                    $paginationUl.html(paginationHtml);

                                    (response.additionalField <= 1) ? $paginationUl.hide() : $paginationUl.show();

                                    if (response.additionalField == 0) {
                                        $(".table-employee > thead").hide();
                                        $(".table-employee > tbody").html("<p>В разделе ещё нет сотрудников!</p>");
                                    } else {
                                        $(".table-employee > thead").show();
                                    }



                                }
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                console.log(jqXHR.status + ' ' + jqXHR.responseText);
                            }
                        });
                    } else {
                        $(".container-table-employee").hide();
                        $(".table-employee tbody").html("");//Очищаем контайнер с информацией о сотрудниках
                    }

                } else {
                    disabledButton();
                    $(".container-info").html("");//Очищаем контайнер с информацией о офисе/подразделении
                    $(".container-table-employee").hide();
                    $(".table-employee tbody").html("");//Очищаем контайнер с информацией о сотрудниках
                }
            }
        );
    });

    /**
     * Удаление офиса/подраздела
     */
    $(".js-remove").on("click", function (e) {
        var type = $(this).data("type");
        var id = $(this).data("id");

        var url;

        switch (type) {
            case 'office':
                url = "/removeOffice"
                break;
            case 'subdivision':
                url = "/removeSubdivision";
                break;
        }

        $.ajax({
            type: "POST",
            url: url,
            context: document.body,
            data: {
                id: id
            },
        }).done(function (response) {
            alert(response);
            location.href = "/";
        });


    });

    /**
     * Сохранение редактированной информации
     * (Вызываем submit формы)
     */
    $(document).on("click", ".js-save-info", function (e) {

        if (!$(this).hasClass("block-btn")) {
            $(".js-update-info").trigger("click");

        }
    });

    $(document).on("click", ".js-update-info", function (e) {
        e.preventDefault();

        //Удаляем ранее отображаемые ошибки
        $("span.error").remove();

        if ($("input[type='checkbox']:checked").data("type") === "office") {
            url = "/updateOffice";
            nameForm = "officeInfo";
        } else {
            nameForm = "subdivisionInfo";
            url = "/updateSubdivision";
        }

        $.post({
            url: url,
            data: $('form[name=' + nameForm + ']').serialize(),
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
        })
    });

    /**
     * Обработчик пагинации
     */
    $(document).on("click", ".js-page-item", function () {
        $(this).find("a").addClass("active-page");
        $('.page-link').not($(this).find("a")).removeClass("active-page");

        var id = $("input[type='checkbox']:checked").data("id");
        var page = +$(this).data("page");

        $.ajax({
            url: "getEmployeesPage",
            type: "post",
            data: {
                subdivisionId: id,
                page: page,
                orderBy: $("input[type='radio']:checked").data('sort'),
            },
            success: function (response) {

                if (response.status === "ok") {
                    $(".table-employee > tbody").html(template.emoloyeeTableRow(response.data));
                    $(".container-table-employee").show();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });


    });

    /**
     * Обработчки сортировки
     */
    $(document).on("click", ".js-sort", function (e) {
        var page = $(".page-link.active-page").closest("li").data("page");
        var id = +$("input[type='checkbox']:checked").data("id");

        typeof page == "undefined" ? page = 0 : '';

        $.ajax({
            url: "getEmployeesPage",
            type: "post",
            data: {
                subdivisionId: id,
                page: page,
                orderBy: $(this).data("sort"),
            },
            success: function (response) {

                if (response.status === "ok") {
                    $(".table-employee > tbody").html(template.emoloyeeTableRow(response.data));
                    $(".container-table-employee").show();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });


    });

});

/**
 * Блокируем кнопки (визуальное отображение)
 */
function disabledButton() {
    $(".btn-info, .btn-employee, .btn-subdivision").addClass("block-btn");
}

function enabledButton() {

    $checkboxChecked = $("input[type='checkbox']:checked");

    if ($checkboxChecked.data("type") === "office") {
        $(".btn-info, .btn-subdivision").removeClass("block-btn");
        $(".btn-employee").hasClass("block-btn") ? "" : $(".btn-employee").addClass("block-btn");
    } else {
        $(".btn-info, .btn-subdivision, .btn-employee").removeClass("block-btn");
    }

}


/* Шаблоны js */

template = {
    officeForm: function (officeInfo) {
        var htmlForm =
            ' <div class="row justify-content-center align-items-center h-100">' +
            '     <form name="officeInfo" action="/updateOffice" method="post">' +
            '         <input type="text" value="' + officeInfo.id + '" readonly name="id" style="display: none;"/>' +
            '         <div class="form-group">' +
            '             <label for="name-office" class="control-label">Наименование</label>\n' +
            '             <input type="text" class="form-control" value="' + officeInfo.name + '" id="name-office" name="name" placeholder="Наименование">' +
            '         </div>' +
            '         <div class="form-group">' +
            '             <label for="address-office" class="control-label">Адрес</label>' +
            '             <input type="text" class="form-control" id="address-office" value="' + officeInfo.address + '" name="address" placeholder="Адрес офиса">' +
            '         </div>' +
            '         <div class="form-group">' +
            '             <button type="submit"  style="display: none;" class="btn btn-primary js-update-info">Обновить</button>' +
            '         </div>' +
            '     </form>' +
            ' </div>';

        return htmlForm;
    },

    subdivisionForm: function (subdivisionInfo) {
        var htmlForm =
            '    <div class="row justify-content-center align-items-center h-100">' +
            '        <form name="subdivisionInfo" action="/updateSubdivision" method="post">' +
            '         <input type="text" value="' + subdivisionInfo.id + '" readonly name="id" style="display: none;"/>' +
            '            <div class="form-group">' +
            '                <label for="name-subdivision" class="control-label">Наименование</label>' +
            '                <input type="text" class="form-control" value="' + subdivisionInfo.name + '" id="name-subdivision" name="name" placeholder="Наименование">' +
            '            </div>' +
            '            <div class="form-group">' +
            '                <label for="fio-head" class="control-label">ФИО руководителя</label>' +
            '                <input type="text" class="form-control" id="fio-head" value="' + subdivisionInfo.fullNameHead + '" name="fullNameHead" placeholder="ФИО руководителя">' +
            '            </div>' +
            '            <div class="form-group">' +
            '                <button type="submit" style="display: none;" class="js-update-info">Обновить</button>' +
            '            </div>' +
            '        </form>' +
            '    </div>';

        return htmlForm;
    },

    emoloyeeTableRow: function (employeeList) {

        var htmlRows = "";
        $.each(employeeList, function (index, item) {
            htmlRows +=
                '<tr>' +
                '    <td>' + item.id + '</td>' +
                '    <td>' + item.fullName + '</td>' +
                '    <td>' + item.dateBirth + '</td>' +
                '    <td>' + item.email + '</td>' +
                '    <td>' + item.phone + '</td>' +
                '    <td><span><i class="fa fa-trash-o icon-trash js-employee-remove" data-id="' + item.id + '" aria-hidden="true"></i></span></td>' +
                '    <td><a href="/employeeEdit/' + item.id + '"><i class="fa fa-pencil-square-o js-go-to-employee-update" data-id="' + item.id + '"  aria-hidden="true"></i></a></td>' +
                '</tr>';
        });

        return htmlRows;
    }
}