<#import "spring.ftl" as spring />
<#import "/WEB-INF/views/layout/defaultlayout.ftl" as layout/>

<@layout.vaultLayout title="Добавление офиса">

<div class="container wrapper h-100">
    <div class="row justify-content-center align-items-center h-100">
        <form name="office" action="/addOffice" method="post">
            <div class="form-group"> <!-- Full Name -->
                <label for="name-office" class="control-label">Наименование</label>
                <input type="text" class="form-control" id="name-office" name="name" placeholder="Наименование">
            </div>
            <div class="form-group"> <!-- Street 1 -->
                <label for="address-office" class="control-label">Адрес</label>
                <input type="text" class="form-control" id="address-office" name="address" placeholder="Адрес офиса">
            </div>
            <div class="form-group"> <!-- Submit Button -->
                <button type="submit" class="btn btn-primary js-add-office">Добавить</button>
                <button type="button" class="btn btn-dark js-redirect-main-page">Отменить</button>
            </div>
        </form>
    </div>

</@layout.vaultLayout>