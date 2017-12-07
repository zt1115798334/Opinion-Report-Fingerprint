$(function () {

    searchSysRoleFun();

    /**
     * 点击搜索操作
     */
    $(document).on("click", ".searchButton", function () {
        searchSysRoleFun();
    });

    /**
     * 新建用户
     */
    $(document).on("click", ".create", function () {
        $("#newRole").modal("show");
    });

    /**
     * 新建用户 -- 保存操作
     */
    $(document).on("click", "#newRole .saveBtn", function () {
        validateFun();
        var bv = $("#roleForm").data('bootstrapValidator');
        bv.validate();
        if (!bv.isValid()) {
            return false;
        }
        var data = $("#roleForm").serializeJSON();
        saveSysRoleFun(data);
    });

    /**
     * 删除
     */
    $(document).on("click", ".delete", function () {
        var roleId = $(this).attr("rowId");
        showBootstrapDialog("确认删除该角色信息？该操作不可恢复！！", callback);

        function callback() {
            delSysRoleFun(roleId);
        }
    });

});

/**
 * 查询所有角色
 */
function searchSysRoleFun() {
    var url = "/system/searchSysRole";
    var roleName = $(".roleName").val();
    var options = {
        columns: [{
            field: 'id',
            title: "角色ID",
            align: "center",
            valign: "middle"
        }, {
            field: 'roleName',
            title: "角色名称",
            align: "left",
            valign: "middle"

        }, {
            field: 'createdDatetime',
            title: "创建时间",
            align: "center",
            valign: "middle"
        }, {
            title: "操作",
            align: "center",
            valign: "middle",
            formatter: function (value, row, index) {
                var _html = "";
                _html += "<button class=\"Jurisdiction\"  type=\"button\" rowId=\"" + row.id + "\">权限管理</button>";
                _html += "<button class=\"delete\"  type=\"button\" rowId=\"" + row.id + "\">删除</button>";
                return _html;
            }
        }],
        contentType: "application/x-www-form-urlencoded",
        paginationPreText: "<i class='glyphicon glyphicon-menu-left'></i>",
        paginationNextText: "<i class='glyphicon glyphicon-menu-right'></i>",
        paginationLoop: false,
        method: 'post',
        pagination: true,
        sidePagination: 'server',
        pageNumber: 1,
        pageSize: 10,
        dataType: "",
        url: url,
        pageList: [10, 25, 50, 100],
        queryParamsType: '',
        formatNoMatches: function () {
            var _nodata = '<div class="text-center"><img src="../../images/no_data.png">'
                + '<p>没有搜到任何数据</p></div>';
            return _nodata;
        },
        onLoadSuccess: function () {
        },
        queryParams: function (params) {
            return {
                roleName: roleName,
                pageSize: params.pageSize,
                pageNumber: params.pageNumber
            }
        }

    };

    $("#table-role").bootstrapTable("destroy").bootstrapTable(options);
}

function validateFun() {
    $("#roleForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            // valid: 'glyphicon glyphicon-ok',
            // invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            roleName: {
                validators: {
                    notEmpty: {
                        message: '请输入角色名称'
                    },
                    stringLength: {
                        max: 20,
                        message: '角色名称不能大于20个字符'
                    },
                    remote: {
                        url: "/system/searchExistByRoleName",
                        type: "post",
                        delay: 1000,
                        // async: false, //改为同步
                        message: '该角色已存在'
                    }
                }
            }
        }
    });
}

/**
 * 保存角色
 */
function saveSysRoleFun(params) {
    var url = "/system/saveSysRole";
    execAjaxJSON(url, params, callback);

    function callback(result) {
        if (result.success) {
            resetForm("#roleForm");
            $("#newRole").modal("hide");
            notify.success({title: "提示", content: result.message, autoClose: true});
            bootstrapTableRefresh();
        } else {
            notify.error({title: "提示", content: result.message});
        }
    }
}

/**
 * 删除角色
 * @param params
 */
function delSysRoleFun(roleId) {

    var url = "/system/delSysRole";
    var params = {
        roleId: roleId
    };
    execAjax(url, params, callback);

    function callback(result) {
        if (result.success) {
            notify.success({title: "提示", content: result.message, autoClose: true});
            bootstrapTableRefresh();
        } else {
            notify.error({title: "提示", content: result.message});
        }
    }
}

/**
 * 角色id查询权限信息
 * @param params
 */
function searchSysPermissionFun(params) {
    var url = "/system/searchSysPermission";
    execAjax(url, params, callback);

    function callback(result) {
        console.log(result);
    }
}

/**
 * 根据角色id保存权限信息
 * @param params
 */
function saveSysPermissionFun(params) {
    var url = "/system/saveSysPermission";
    execAjax(url, params, callback);

    function callback(result) {
        console.log(result);
    }
}


function bootstrapTableRefresh() {
    $("#table-role").bootstrapTable('refresh');
}
