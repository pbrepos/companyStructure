<#import "spring.ftl" as spring />
<#import "/WEB-INF/views/layout/defaultlayout.ftl" as layout/>

<@layout.vaultLayout title="Добавление сотрудника">

<div class="container wrapper h-100">
    <div class="row justify-content-center align-items-center h-100">
        <form name="employee" action="/addEmployee" method="post">
            <div class="form-group">
                <label for="name-employee" class="control-label">ФИО</label>
                <input type="text" class="form-control" id="name-employee" name="fullName" placeholder="ФИО">
            </div>
            <div class="form-group">
                <label for="data-birth" class="control-label">Дата рождения</label>
                <input type="date" class="form-control" id="data-birth" name="dateBirth" placeholder="Дата рождения">
            </div>

            <div class="form-group">
                <label for="phone-employee" class="control-label">Телефон</label>
                <input type="number" class="form-control" id="phone-employee" name="phone" placeholder="Телефон">
            </div>

            <div class="form-group">
                <label for="email-employee" class="control-label">Email</label>
                <input type="email" class="form-control" id="email-employee" name="email" placeholder="Email">
            </div>
            <input type="hidden" name="subdivisionId" value="${subdivisionId}" />
            <div class="form-group"> <!-- Submit Button -->
                <button type="submit" data-subdivision-id="${subdivisionId}" class="btn btn-primary js-add-employee">Добавить</button>
                <button type="button" class="btn btn-dark js-redirect-main-page">Отменить</button>
            </div>
        </form>
    </div>

</@layout.vaultLayout>