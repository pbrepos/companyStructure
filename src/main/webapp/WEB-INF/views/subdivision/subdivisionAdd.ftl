<#import "spring.ftl" as spring />
<#import "/WEB-INF/views/layout/defaultlayout.ftl" as layout/>

<@layout.vaultLayout title="Добавление подраздела">


<div class="container wrapper h-100">
    <div class="row justify-content-center align-items-center h-100">
        <form id="addSubdivisionForm" name="subdivision" action="/addSubdivision" method="post">
            <div class="form-group"> <!-- Full Name -->
                <label for="name-subdivision" class="control-label">Наименование</label>
                <input type="text" class="form-control" id="name-subdivision" name="name" placeholder="Наименование">
            </div>
            <div class="form-group"> <!-- Street 1 -->
                <label for="fio-head" class="control-label">ФИО руководителя</label>
                <input type="text" class="form-control" id="fio-head" name="fullNameHead" placeholder="ФИО руководителя">
            </div>

            <input type="hidden" name="type" value="${type}" />

            <input type="hidden" name="id" value="${id}" />
            <div class="form-group"> <!-- Submit Button -->
                <button type="submit" data-type="${type}" data-id="${id}" class="btn btn-primary btn-add-subdivision js-add-subdivision">Добавить</button>
                <button type="button" class="btn btn-dark js-redirect-main-page">Отменить</button>
            </div>
        </form>
    </div>

</@layout.vaultLayout>