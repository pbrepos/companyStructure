<#macro vaultLayout title="Company structure">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>

<head>
    <#include "imports.ftl"/>
    <title>${title}</title>
<body>


    <#include "header.ftl"/>

    <#nested/>

    <#include "footer.ftl"/>
</div>
</body>
</html>
</#macro>