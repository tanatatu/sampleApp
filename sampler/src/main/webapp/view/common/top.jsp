<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=0.6, maximum-scale=1.5, user-scalable=yes" />

	<link rel="stylesheet" href="css/base.css" />
	<link rel="stylesheet" href="js/jQuery/tablesorter/themes/blue/style.css" />

	<!--Business Application System Executer 6  -->
	<title>在庫管理システム</title>
</head>

<body>

	<!-- Main Menu  -->
	<div id="ebase6_mainmenu">
		<!--Excellence Business Application System Executer 6  -->
		在庫管理システム
	</div>

	<!-- Sub Menu -->
	<div id="ebase6_submenu">
		作業選択
	</div>

	<!-- Sub Menu List-->
	<!--  お引越し
	<table>
	<tr><td><a id="goodsView" href="javascript:void(0)">品物一覧 </a></td>
	<td><a id="stockManegement" href="javascript:void(0)">在庫管理 </a></td>
	<td><a id="orderingWork" href="javascript:void(0)">発注作業 </a></td>
	<td><a id="goodsCheck" href="javascript:void(0)">品物検品 </a></td>
	<td><a id="Inventories" href="javascript:void(0)">棚卸作業 </a></td>
	<td><a id="History" href="javascript:void(0)">履歴表示 </a></td></tr>
	</table>
-->
	<!-- Body  -->
	<div id="ebase6_body">
	    <div id="ebase6_nav">

		<!-- レイアウトは良い感じに直して -->
		<table id = "table_item">
		<tr>
		<td><a id="goodsView" href="javascript:void(0)">品物一覧 </a></td>
		<td><a id="stockManegement" href="javascript:void(0)">在庫管理 </a></td>
		<td><a id="orderingWork" href="javascript:void(0)">発注作業 </a></td>
		<td><a id="goodsCheck" href="javascript:void(0)">品物検品 </a></td>
		<td><a id="Inventories" href="javascript:void(0)">棚卸作業 </a></td>
		<td><a id="History" href="javascript:void(0)">履歴表示 </a></td>
		</tr>
		</table>

	    	<!-- Mentenuce Menu List -->
			<div id="ebase6_menulist">
				<div id="ebase6_menulist_title">Mentenunce</div>
				<ul>
					<li><a id="initialsetup" href="javascript:void(0)">Initial Setup</a></li>
					<li><a id="xmlexe" href="javascript:void(0)">XML Execute</a></li>
					<li><a id="dbexe" href="javascript:void(0)">DB SQL Execute</a></li>
					<li><a id="addWork" href="javascript:void(0)">Add Work 20180322 Enya</a></li>
				</ul>
			</div>

		</div>

		<div id="datafield"></div>
	</div>



	<!-- Control Menu  //-->
	<div id="ebase6_controlmenu">
		<input id="ebase6_conmenu_mente" type="image" src="view/image/ico_mente_57_57.png" />
	</div>

	<!-- Shadow  -->
	<div id="ebase6_shadow"></div>

	<!-- Initial Page -->
	<div id="ebase6_initial_body">
<pre>
This is a development project for ebase's new version 6.
It is currently under construction and can not be used.
We are promoting development so that it can be released in 2018.
Those who can cooperate with us can contact yoshizawa@1excellence.com.

Efforts in the new version
· Let html 5 be the basic structure of the screen
· Re-create a new framework without using excellence framework 2
· Simplified interface
· Main usage for smartphone browser
· Dynamic site using Ajax
· Refactoring all ebase data
</pre>
		<input type="button" id="ebase6_logon" value="ok">
	</div>

	<!-- Popup -->
	<div id="ebase6_popup">
		<div id="ebase6_popup_head">
			<div id="ebase6_popup_title"></div>
			<input type="image" src="view/image/icon_close_32.png" id="ebase6_popup_close" style="float:right;right:0;" />
			<div style="clear:both"></div>
    	</div>
    	<div id="ebase6_popup_body">
    	</div>
    	<div id="ebase6_popup_foot">
    	</div>
	</div>

</body>

<!-- スクリプト //-->
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jQuery/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="js/ebase6.js"></script>

</html>