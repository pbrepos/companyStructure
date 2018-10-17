<#import "spring.ftl" as spring />
<#import "WEB-INF/views/layout/defaultlayout.ftl" as layout/>
<@layout.vaultLayout title="Структура компании">

<div class="container wrapper">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-4">
                    <div class="row">
                        <div class="col-md-12">
                            <button type="button"
                                    class="btn-sm btn btn-block btn-outline-primary js-redirect-add-office">
                                Добавить офис
                            </button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 tree-container">
                            <div class="checkbox">
                                <ul class="ul-tree">
                              <#list offices as offce>
                                  <li>
                                      <label class="form-checkbox form-icon" for="s_fac-o-${offce.id}">
                                          <input id="s_fac-o-${offce.id}" data-type="office" type="checkbox"
                                                 data-id="${offce.id}"
                                                 class="sev_check">${offce.name}
                                      </label>
                                       <#if !offce.subdivisions?has_content>
                                                  <i class="fa fa-trash-o icon-trash js-remove" data-type="office"
                                                     data-id="${offce.id}" aria-hidden="true"></i>
                                       </#if>
                                  </li>
                                  <#list offce.subdivisions as subdivision>
                                      <#if !subdivision.parentSubdivision?? && subdivision.childrenSubdivision?has_content>
                                        <ul>
                                            <li>
                                                <label class="form-checkbox form-icon" for="s_fac-${subdivision.id}">
                                                    <input id="s_fac-${subdivision.id}" data-type="subdivision"
                                                           data-id="${subdivision.id}" type="checkbox"
                                                           class="sev_check">${subdivision.name}
                                                </label>
                                                <i class="fa fa-trash-o icon-trash js-remove" data-type="subdivision"
                                                   data-id="${subdivision.id}" aria-hidden="true"></i>
                                                           <@treeView subdivision subdivision.childrenSubdivision/>
                                            </li>
                                        </ul>
                                      <#elseif !subdivision.parentSubdivision?? && !subdivision.childrenSubdivision?has_content>
                                        <ul>
                                            <li>
                                                <label class="form-checkbox form-icon" for="s_fac-${subdivision.id}">
                                                    <input id="s_fac-${subdivision.id}" data-type="subdivision"
                                                           data-id="${subdivision.id}" type="checkbox"
                                                           class="sev_check">${subdivision.name}
                                                </label>
                                                <i class="fa fa-trash-o icon-trash js-remove" data-type="subdivision"
                                                   data-id="${subdivision.id}" aria-hidden="true"></i>
                                            </li>
                                        </ul>

                                      </#if>
                                  </#list>
                              </#list>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="row information-container">
                        <div class="col-md-12 container-info">

                        </div>
                    </div>
                    <div class="row button-container">
                        <div class="col-md-4">
                            <button type="button"
                                    class="btn btn-md btn-block btn-outline-info block-btn btn-info js-save-info">
                                Сохранить
                            </button>
                        </div>
                        <div class="col-md-4">
                            <button type="button"
                                    class="btn btn-block btn-outline-info btn-subdivision block-btn js-go-to-add-subdivision">
                                Добавить подразделение
                            </button>
                        </div>
                        <div class="col-md-4">
                            <button type="button"
                                    class="btn btn-block btn-outline-info btn-employee block-btn js-go-to-add-employee">
                                Добавить сотрудника
                            </button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 container-table-employee">
                            <table class="table table-hover table-sm table-employee">
                                <thead>
                                <tr>
                                    <th>
                                        <div class="custom-control custom-radio custom-control-inline">
                                            <input type="radio" class="custom-control-input js-sort" data-sort="id" id="defaultInline1" name="inlineDefaultRadiosExample">
                                            <label class="custom-control-label" for="defaultInline1">#</label>
                                        </div>

                                    </th>
                                    <th>
                                        <div class="custom-control custom-radio custom-control-inline">
                                            <input type="radio" class="custom-control-input js-sort" data-sort="fullName" id="defaultInline2" name="inlineDefaultRadiosExample" checked>
                                            <label class="custom-control-label" for="defaultInline2">ФИО</label>
                                        </div>
                                    </th>
                                    <th>
                                        <div class="custom-control custom-radio custom-control-inline">
                                            <input type="radio" class="custom-control-input js-sort" data-sort="dateBirth" id="defaultInline3" name="inlineDefaultRadiosExample">
                                            <label class="custom-control-label" for="defaultInline3"> Дата рождения</label>
                                        </div>
                                    </th>
                                    <th>
                                        <div class="custom-control custom-radio custom-control-inline">
                                            <input type="radio" class="custom-control-input js-sort" data-sort="email" id="defaultInline4" name="inlineDefaultRadiosExample">
                                            <label class="custom-control-label" for="defaultInline4"> Email</label>
                                        </div>
                                    </th>
                                    <th>
                                        <div class="custom-control custom-radio custom-control-inline">
                                            <input type="radio" class="custom-control-input js-sort" data-sort="phone" id="defaultInline5" name="inlineDefaultRadiosExample">
                                            <label class="custom-control-label" for="defaultInline5"> Телефон</label>
                                        </div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                            <div class="container-pagination">
                                <div class="row">
                                    <div class="col-md-12 d-flex justify-content-center">
                                        <nav aria-label="pagination">
                                            <ul class="pagination pagination-ul">
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

 <#macro treeView subdivision listSubdivision>
     <#list listSubdivision as suddivision>
         <#if (suddivision.parentSubdivision.id == subdivision.id) >
          <ul>
              <li>
                  <label class="form-checkbox form-icon" for="s_fac-${suddivision.id}">
                      <input id="s_fac-${suddivision.id}" data-type="subdivision"
                             data-id="${suddivision.id}" type="checkbox"
                             class="sev_check">${suddivision.name}
                  </label>
                  <i class="fa fa-trash-o icon-trash js-remove" data-type="subdivision" data-id="${suddivision.id}"
                     aria-hidden="true"></i>
                    <@treeView suddivision suddivision.childrenSubdivision/>
              </li>
          </ul>
         </#if>
     </#list>
 </#macro>

</@layout.vaultLayout>

