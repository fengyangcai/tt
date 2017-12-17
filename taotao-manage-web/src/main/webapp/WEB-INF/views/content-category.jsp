<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory">
    </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
	$("#contentCategory").tree({
		url : '/rest/content/category',
		method : "GET",
		animate: true,
		onContextMenu: function(e,node){//在右键点击节点的时候触发。
			//阻止显示浏览器默认的右击菜单
            e.preventDefault();
			//this表示当前操作的这棵树；将当前右击的节点选中
            $(this).tree('select',node.target);
			//选取id为contentCategoryMenu的元素，渲染为easyui menu组件调用显示方法显示具体的菜单
			//并将该菜单显示在当前鼠标右击的坐标
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
        },
        onAfterEdit : function(node){//在编辑节点之后触发。
        	var _tree = $(this);
        	if(node.id == 0){//添加
        		// 新增节点
        		$.post("/rest/content/category/add",{parentId:node.parentId,name:node.text},function(data){
        			//保存新节点到数据库后将新节点的id更新为数据库中实际节点的id
        			_tree.tree("update",{
        				target : node.target,
        				id : data.id
        			});
        		});
        	}else{//重命名
        		$.ajax({
        			   type: "POST",
        			   url: "/rest/content/category/update",
        			   data: {id:node.id,name:node.text},
        			   success: function(msg){
        				   //$.messager.alert('提示','新增商品成功!');
        			   },
        			   error: function(){
        				   $.messager.alert('提示','重命名失败!');
        			   }
        			});
        	}
        }
	});
});
//当点击 菜单项 的时候触发
function menuHandler(item){
	//获取当前选中节点对应的那颗树
	var tree = $("#contentCategory");
	var node = tree.tree("getSelected");//获取当前选中节点
	
	if(item.name === "add"){//当前选择的菜单的名称为add的话---添加
		//在该树上面追加节点，而且该节点的父节点则为当前选中的节点
		tree.tree('append', {
            parent: (node?node.target:null),//新节点的父节点为：刚刚选中的节点
            data: [{
                text: '新建分类',//新节点的默认名称
                id : 0,//新节点的id为0
                parentId : node.id
            }]
        }); 
		//在树上查询id为0的节点也即新节点
		var _node = tree.tree('find',0);
		//选中新节点，并开始编辑新节点。
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	}else if(item.name === "rename"){//重命名
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){//删除
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			//当用户点击“确定”按钮的时侯将传递一个true值给回调函数，否则传递一个false值。 
			if(r){
				$.ajax({
     			   type: "POST",
     			   url: "/rest/content/category/delete",
     			   data : {parentId:node.parentId,id:node.id},
     			   success: function(msg){
     				  tree.tree("remove",node.target);
     			   },
     			   error: function(){
     				   $.messager.alert('提示','删除失败!');
     			   }
     			});
			}
		});
	}
}
</script>