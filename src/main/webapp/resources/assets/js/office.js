$(document).ready(function (e) {
    /**
     * Редирект на главную страницу
     */
    $(".js-redirect-main-page").click(function (e) {
        location.href = "/";
    });

    /**
     * Добавления нового офиса
     */
    $('.js-add-office').click(function(e) {
        e.preventDefault();

        //Удаляем ранее отображаемые ошибки
        $('input').next().remove();

        $.post({
            url : '/addOffice',
            data : $('form[name=office]').serialize(),
            success : function(res) {

                if(res.validated){
                    location.href = "/";
                }else{
                    //Показать ошибки валидации
                    $.each(res.errorMessages,function(key,value){
                        $('input[name='+key+']').after('<span class="error">'+value+'</span>');
                    });
                }
            }
        })
    });

    

});