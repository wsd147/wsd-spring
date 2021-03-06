var User = {
    userTableId:'userTable',
    tableobjct: null,
	table:null,
    prefix :"/sys/user",
	treeOnclickId:null,
    width:null
};

//初始化表格列
User.initColumn = function(){
    var columns = [[
        {field: 'selectItem', checkbox: true},
        {title: '帐号', field: 'username', align: 'center',width:'10%'},
        {title: '姓名', field: 'realname', align: 'center'},
        {title: '头像', field: 'avatar', align: 'center', templet:function(d){
            return "<img src="+d.avatar+" width='64' height='64'/>";
		}},
        {title: '电话', field: 'phone', align: 'center'},
        {title: '邮箱', field: 'email', align: 'center'},
        {title: '性别', field: 'sex', align: 'center',width:'6%', templet:function(d){
        	var s = null
           if(d.sex==0){
           		s = "<span class='layui-badge layui-bg-red'>女</span>";
		   }else if(d.sex == 1){
               s = "<span class='layui-badge layui-bg-blue'>男</span>"
		   }
		   return s;
		}},
        {title: '状态', field: 'locked', align: 'center',width:'6%', templet:function(d){
            if(d.locked==0){
                return "<span class='layui-badge layui-bg-red'><i class='fa fa-lock'></i></span>";
            }else if(d.locked == 1){
                return "<span class='layui-badge layui-bg-blue'><i class='fa fa-unlock'></span>"
            }else{
                return null;
            }
        }},
        {title: '创建时间', field: 'ctime', align: 'center'}
        ]];
    return columns;
};

//加载表格
User.loadTable =function(){
	layui.use(['table'],function(){
        User.tableobjct = layui.table;
        var defaultColunms = User.initColumn();
        var table = new LAYtable(User.userTableId, User.prefix+"/list/", defaultColunms);
        table.setTitle("用户列表");
        User.table = table.init(User.tableobjct);
	});
}

User.reloadTable = function(){
    var queryData = {};
    queryData['username'] = $("#searchName").val().trim()==''?undefined:$("#searchName").val();
    queryData['orgnId'] = User.orgnId;
    User.table.refresh(queryData);
}

User.loadOrginTree = function(elem,onclickFun){
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback : {
            onClick : onclickFun
        }
    };
    var origanTree = new $ZTree(elem, User.prefix+"/getOrignTree");
    origanTree.setSettings(setting);
    origanTree.init();
}

//检查是否选中列
User.checkSelect = function () {

};

//打开添加页面
User.openAdd = function () {
    layer.open({
        type : 2,
        title : '增加用户',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ User.width, '500px' ],
        content : User.prefix + '/add'
    });
}

//打开修改页面
User.openEdit = function () {
    layer.open({
        type : 2,
        title : '用户修改',
        maxmin : true,
        shadeClose : false,
        area : [ User.width, '500px' ],
        content : User.prefix + '/edit/' + id // iframe的url
    });
}

//用户保存
User.save = function () {

}

//删除用户
User.batchRemove = function () {
    var checkStatus = User.tableobjct.checkStatus(User.userTableId);
    if(checkStatus.data.length==0){
        Alert.warn("至少选择一行数据");
    }else{
        layer.confirm("确定删除选中用户吗？", {icon: 3, title:'提示'}, function(index){
            var ids = [];
            $.each(checkStatus.data,function (index,item) {
                ids.push(item.userId);
            })
            var ajx = new $ax(User.prefix+"/removeBatch",function (data) {
                if(data.code == 200){
                    Alert.ok(data.message);
                    User.loadTable();
                }else {
                    Alert.error(data.message);
                }
            },function () {
                Alert.error("网络错误");
            });
            ajx.set("ids",ids);
            ajx.setType("post");
            ajx.start();
            layer.close(index);
        });
    }
}

//禁用/启用账户
User.disableAndEnable =function () {
	
}
$(function(){
    User.width = $('#'+User.userTableId).parent().width()+'px';
	User.loadTable();
    User.loadOrginTree("organTree",function (event, treeId, treeNode) {
      	User.orgnId = treeNode.id;
      	User.reloadTable()
    });
});


function load(deptId) {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				// showRefresh : true,
				// showToggle : true,
				// showColumns : true,
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						name : $('#searchName').val(),
						deptId : deptId
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
					{
						checkbox : true
					},
					{
						field : 'userId', // 列字段名
						title : '序号' // 列标题
					},
					{
						field : 'name',
						title : '姓名'
					},
					{
						field : 'username',
						title : '用户名'
					},
					{
						field : 'email',
						title : '邮箱'
					},
					{
						field : 'status',
						title : '状态',
						align : 'center',
						formatter : function(value, row, index) {
							if (value == '0') {
								return '<span class="label label-danger">禁用</span>';
							} else if (value == '1') {
								return '<span class="label label-primary">正常</span>';
							}
						}
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.userId
								+ '\')"><i class="fa fa-edit "></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
								+ row.userId
								+ '\')"><i class="fa fa-remove"></i></a> ';
							var f = '<a class="btn btn-success btn-sm ' + s_resetPwd_h + '" href="#" title="重置密码"  mce_href="#" onclick="resetPwd(\''
								+ row.userId
								+ '\')"><i class="fa fa-key"></i></a> ';
							return e + d + f;
						}
					} ]
			});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	// iframe层
	layer.open({
		type : 2,
		title : '增加用户',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add'
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : "/sys/user/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})
}
function edit(id) {
	layer.open({
		type : 2,
		title : '用户修改',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function resetPwd(id) {
	layer.open({
		type : 2,
		title : '重置密码',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '400px', '260px' ],
		content : prefix + '/resetPwd/' + id // iframe的url
	});
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['userId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}
function getTreeData() {
	$.ajax({
		type : "GET",
		url : "/system/sysDept/tree",
		success : function(tree) {
			loadTree(tree);
		}
	});
}
function loadTree(tree) {
	$('#jstree').jstree({
		'core' : {
			'data' : tree
		},
		"plugins" : [ "search" ]
	});
	$('#jstree').jstree().open_all();
}
$('#jstree').on("changed.jstree", function(e, data) {
	if (data.selected == -1) {
		var opt = {
			query : {
				deptId : '',
			}
		}
		$('#exampleTable').bootstrapTable('refresh', opt);
	} else {
		var opt = {
			query : {
				deptId : data.selected[0],
			}
		}
		$('#exampleTable').bootstrapTable('refresh',opt);
	}

});