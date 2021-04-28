/**
 * javascript for ebase6
 */
$(function(){

	var messages = {}; //メッセージ配列
	var msgidlist = [ 'TOPDBEXE','BTNSUBMIT','BTNSUBMIT','BTNSUBMIT','LBXMLKEY','LBXMLSELCT','ADDWORK','InsertData' ];

	//POST処理作成
	$.postJSON = function(url, data, callback) {
		$.post(url, data, callback, "json");
	};

	//初回稼働時
	$(document).ready(function(){
		//ラングコードを取得
		var lang = navigator.language || navigator.userLanguage;
		if(lang != "ja") lang="en";
		//メッセージ配列の生成
		$.postJSON("DQube",{actionID:'MSGGET01' ,ids:msgidlist , lang:lang}, function(jres){
			for(j=0;j<msgidlist.length;j++){
				id = msgidlist[j];
				messages[id] = jres.message[id];
			}
		});
	});

	//ALL menu close
	function menuClose() {
		$('#ebase6_shadow').css('display', 'none');
		$('#ebase6_menulist').css('display', 'none');
		$('#ebase6_popup').css('display', 'none');
	};

	//初期処理
	$('a[id=initialsetup]').click(function(){
		$.postJSON("DQube",{actionID:'INITILIZ'}, function(jres){
			alert(jres.result["result"]);
			return false;
		});
	});

	/*function time() {

	var field = document.getElementById("datafield");

	var hiduke=new Date();
    var jikan= new Date();

	//年・月・日・曜日を取得する
	var year = hiduke.getFullYear();
	var month = hiduke.getMonth()+1;
	var week = hiduke.getDay();
	var day = hiduke.getDate();

	//時・分・秒を取得する
 	var hour = jikan.getHours();
 	var minute = jikan.getMinutes();
  	var second = jikan.getSeconds();

	var yobi= new Array("日","月","火","水","木","金","土");

	var timeView = document.write(year+"/"+month+"/"+day+"/ "+"("+yobi[week]+")"+hour+":"+minute);
	timeView.style.cssText = 'position:fixed;top:20px;right:20px;left:100px';
	}*/


	// 2018-03-22 M.Enya Add
	// 追加処理
	function addWork() {
		//var sql = prompt("input sql","");
		var id = $('#ebase6_popup_id').val();
		var value = $('#ebase6_popup_value').val();

		$('#dataTable').remove();
		$('#ebase6_pamview').remove();

		var field = document.getElementById("datafield");

		var pamView = document.createElement("div");
		field.appendChild(pamView);
		pamView.className = "ebase6_pamview";
		pamView.id = "ebase6_pamview";

		var table = document.createElement("table");
		field.appendChild(table);
		table.className = "tablesorter";
		table.id = "dataTable";

		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'ADDWORK01',id:id, value:value}, function(jres){

			pamView.innerHTML="SQL [ " + jres.pams["sql"] + " ]";

			//DOM型で要素をAppendしていく
			var theadElem = document.createElement("thead");
			var trElem = document.createElement("tr");
			table.appendChild(theadElem);
			theadElem.appendChild(trElem);

			var ssSearch = document.getElementById("ss_select");

			for(i=0;i<jres.keys.length;i++){
				//テーブルにカラム名を表示
				var col = jres.keys[i];
				var thElem = document.createElement("th");
				trElem.appendChild(thElem);
				thElem.className = jres.tblColData[col]["classname"];
				thElem.innerHTML=jres.tblColData[col]["name"];
			}

			//データ行を作成
			var tbodyElem = document.createElement("tbody");
			table.appendChild(tbodyElem);

			//データのヒットがない場合、空行を作成
			if(jres.tblData.length==0){
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
				}
			}

			for(j=0;j<jres.tblData.length;j++){ //データの書きだし
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
					tdElem.style.background = "#fff";
					var col = jres.keys[i];
					tdElem.innerHTML = jres.tblData[j][col];
				}
			}


			$("#dataTable").tablesorter({
				widgets: ['zebra'],
				sortList: [[0, 1]]
			});
			$("#dataTable").trigger("update");

			//$.ajaxSetup({ async: true }); //同期の解除
			return false;
		});
	}
	$('a[id=addWork]').click(function(){
		$('#ebase6_popup').css('width', '300');
		$('#ebase6_popup').css('height', '200');
		$('#ebase6_popup').css('margin', '-150px 0 0 -100px');
		$('#ebase6_shadow').css('display', 'block'); //他入力欄をシャドウ化
		$('#ebase6_popup').css('display', 'block'); //ポップアップ表示
		$('#ebase6_popup_title').html(messages['ADDWORK']); //ポップアップにメッセージ表示
		$('#ebase6_popup_body').empty(); //ボディ初期化
		$('#ebase6_popup_foot').empty(); //フッター初期化
		//実行ボタン作成
		var btn = document.createElement("input");
		btn.setAttribute('type',"button");
		btn.setAttribute('value',messages['BTNSUBMIT']);
		btn.setAttribute('id',"ebase6_popup_submit");
		$('#ebase6_popup_foot').append(btn);
		$('#ebase6_popup_submit').off("click"); //実行ボタンの処理を初期化
		$('#ebase6_popup_submit').on("click" , addWork ); //実行ボタンの処理変更
		$('#ebase6_popup_submit').on("click" , menuClose ); //ポップアップを閉じる
		//入力欄作成
		var id_inp = document.createElement("textarea");
		id_inp.setAttribute('id',"ebase6_popup_id");
		id_inp.style.cssText = 'position:absolute;left:0;width:128px;height:64px;';
		$('#ebase6_popup_body').append(id_inp);
		//入力欄作成
		var value_inp = document.createElement("textarea");
		value_inp.setAttribute('id',"ebase6_popup_value");
		value_inp.style.cssText = 'position:absolute;right:0;width:128px;height:64px;';
		$('#ebase6_popup_body').append(value_inp);
	});
	// 2018-03-22 M.Enya Add
	//DB処理
	function sqlExecute() {
		//var sql = prompt("input sql","");
		var sql = $('#ebase6_popup_sql').val();

		$('#dataTable').remove();
		$('#ebase6_pamview').remove();

		var field = document.getElementById("datafield");

		var pamView = document.createElement("div");
		field.appendChild(pamView);
		pamView.className = "ebase6_pamview";
		pamView.id = "ebase6_pamview";

		var table = document.createElement("table");
		field.appendChild(table);
		table.className = "tablesorter";
		table.id = "dataTable";

		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'SQLEXE01',sqltxt:sql}, function(jres){

			pamView.innerHTML="SQL [ " + jres.pams["sql"] + " ]";

			//DOM型で要素をAppendしていく
			var theadElem = document.createElement("thead");
			var trElem = document.createElement("tr");
			table.appendChild(theadElem);
			theadElem.appendChild(trElem);

			var ssSearch = document.getElementById("ss_select");

			for(i=0;i<jres.keys.length;i++){
				//テーブルにカラム名を表示
				var col = jres.keys[i];
				var thElem = document.createElement("th");
				trElem.appendChild(thElem);
				thElem.className = jres.tblColData[col]["classname"];
				thElem.innerHTML=jres.tblColData[col]["name"];
			}

			//データ行を作成
			var tbodyElem = document.createElement("tbody");
			table.appendChild(tbodyElem);

			//データのヒットがない場合、空行を作成
			if(jres.tblData.length==0){
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
				}
			}

			for(j=0;j<jres.tblData.length;j++){ //データの書きだし
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
					tdElem.style.background = "#fff";
					var col = jres.keys[i];
					tdElem.innerHTML = jres.tblData[j][col];
				}
			}


			$("#dataTable").tablesorter({
				widgets: ['zebra'],
				sortList: [[0, 1]]
			});
			$("#dataTable").trigger("update");

			//$.ajaxSetup({ async: true }); //同期の解除
			return false;
		});
	}
	$('a[id=dbexe]').click(function(){
		$('#ebase6_popup').css('width', '300');
		$('#ebase6_popup').css('height', '200');
		$('#ebase6_popup').css('margin', '-150px 0 0 -100px');
		$('#ebase6_shadow').css('display', 'block'); //他入力欄をシャドウ化
		$('#ebase6_popup').css('display', 'block'); //ポップアップ表示
		$('#ebase6_popup_title').html(messages['TOPDBEXE']); //ポップアップにメッセージ表示
		$('#ebase6_popup_body').empty(); //ボディ初期化
		$('#ebase6_popup_foot').empty(); //フッター初期化
		//実行ボタン作成
		var btn = document.createElement("input");
		btn.setAttribute('type',"button");
		btn.setAttribute('value',messages['BTNSUBMIT']);
		btn.setAttribute('id',"ebase6_popup_submit");
		$('#ebase6_popup_foot').append(btn);
		$('#ebase6_popup_submit').off("click"); //実行ボタンの処理を初期化
		$('#ebase6_popup_submit').on("click" , sqlExecute ); //実行ボタンの処理変更
		$('#ebase6_popup_submit').on("click" , menuClose ); //ポップアップを閉じる
		//入力欄作成
		var inp = document.createElement("textarea");
		inp.setAttribute('id',"ebase6_popup_sql");
		inp.style.cssText = 'position:absolute;left:0;width:295px;height:128px;';
		$('#ebase6_popup_body').append(inp);
	});

	//XML処理
	function xmlExecute() {
		var xml = $('#ebase6_popup_xml').val();
		var key = $('#ebase6_popup_xmlkey').val();

		$('#dataTable').remove();
		$('#ebase6_pamview').remove();

		var field = document.getElementById("datafield");

		var pamView = document.createElement("div");
		field.appendChild(pamView);
		pamView.className = "ebase6_pamview";
		pamView.id = "ebase6_pamview";

		var table = document.createElement("table");
		field.appendChild(table);
		table.className = "tablesorter";
		table.id = "dataTable";

		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'XMLEXE01' , xmlfile:xml, xmlKey:key}, function(jres){

			pamView.innerHTML="XML [ " + jres.pams["xml"] + " ] KEY [ " + jres.pams["key"] + " ]";

			//DOM型で要素をAppendしていく
			var theadElem = document.createElement("thead");
			var trElem = document.createElement("tr");
			table.appendChild(theadElem);
			theadElem.appendChild(trElem);

			var ssSearch = document.getElementById("ss_select");

			for(i=0;i<jres.keys.length;i++){
				//テーブルにカラム名を表示
				var col = jres.keys[i];
				var thElem = document.createElement("th");
				trElem.appendChild(thElem);
				thElem.className = jres.tblColData[col]["classname"];
				thElem.innerHTML=jres.tblColData[col]["name"];

			}

			//データ行を作成
			var tbodyElem = document.createElement("tbody");
			table.appendChild(tbodyElem);

			//データのヒットがない場合、空行を作成
			if(jres.tblData.length==0){
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
				}
			}

			for(j=0;j<jres.tblData.length;j++){ //データの書きだし
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
					tdElem.style.background = "#fff";
					var col = jres.keys[i];
					tdElem.innerHTML = jres.tblData[j][col];
				}
			}


			$("#dataTable").tablesorter({
				widgets: ['zebra'],
				sortList: [[0, 1]]
			});
			$("#dataTable").trigger("update");

			//$.ajaxSetup({ async: true }); //同期の解除
			return false;
		});
	}
	$('a[id=xmlexe]').click(function(){
		$('#ebase6_popup').css('width', '300');
		$('#ebase6_popup').css('height', '200');
		$('#ebase6_popup').css('margin', '-150px 0 0 -100px');
		$('#ebase6_shadow').css('display', 'block'); //他入力欄をシャドウ化
		$('#ebase6_popup').css('display', 'block'); //ポップアップ表示
		$('#ebase6_popup_title').html(messages['TOPXMLEXE']); //ポップアップにメッセージ表示
		$('#ebase6_popup_body').empty(); //ボディ初期化
		$('#ebase6_popup_foot').empty(); //フッター初期化
		//実行ボタン作成
		var btn = document.createElement("input");
		btn.setAttribute('type',"button");
		btn.setAttribute('value',messages['BTNSUBMIT']);
		btn.setAttribute('id',"ebase6_popup_submit");
		$('#ebase6_popup_foot').append(btn);
		$('#ebase6_popup_submit').off("click"); //実行ボタンの処理を初期化
		$('#ebase6_popup_submit').on("click" , xmlExecute ); //実行ボタンの処理変更
		$('#ebase6_popup_submit').on("click" , menuClose ); //ポップアップを閉じる
		//入力欄作成
		var $label1 = $('<label>'+messages['LBXMLSELCT']+'</label>');
		$('#ebase6_popup_body').append($label1);
		$('#ebase6_popup_body').append("<br>");
		var sect = document.createElement("select");
		sect.setAttribute('id',"ebase6_popup_xml");
		sect.style.cssText = 'width:295px';
		$('#ebase6_popup_body').append(sect);
		var characters = {
			System: 'control/system.xml',
			Control: 'control/control.xml',
			Message: 'control/message.xml',
			Column: 'control/colname.xml'
			},
			$select = $('#ebase6_popup_xml'),
		    $option,
		    isSelected;
		$.each(characters, function (name, value) {
		    isSelected = (value === 'Control');
		    $option = $('<option>')
		        .val(value)
		        .text(name)
		        .prop('selected', isSelected);
		    $select.append($option);
		});
		$('#ebase6_popup_body').append("<br>");
		var $label2 = $('<label>'+messages['LBXMLKEY']+'</label>');
		$('#ebase6_popup_body').append($label2);
		$('#ebase6_popup_body').append("<br>");
		var inp = document.createElement("input");
		inp.setAttribute('type',"text");
		inp.setAttribute('id',"ebase6_popup_xmlkey");
		$('#ebase6_popup_body').append(inp);
	});

	//メンテナンスボタンをクリック
	$('#ebase6_conmenu_mente').click(function(){
		if($('#ebase6_menulist').css('display') == "block"){
			menuClose();
		}else{
			$('#ebase6_menulist').css('display', 'block');
		}

	});

	//ログオン処理
	$('#ebase6_logon').click(function(){
		$('#ebase6_initial_body').css('display', 'none');
		$('#ebase6_shadow').css('display', 'none');
	});

	//ポップアップクローズ
	$('#ebase6_popup_close').click(function(){
		$('#ebase6_popup').css('display', 'none');
		$('#ebase6_shadow').css('display', 'none');
	});

	/* NAVIGATION FRAME */
	var navPX = $("#ebase6_nav").offset().left + window.pageXOffset;
	var navPY = $("#ebase6_nav").offset().top + window.pageYOffset;
	var winTop = $("#ebase6_body").scrollTop();
	$("#ebase6_nav").css('position','absolute');
	$("#ebase6_nav").animate({top: winTop + "px"}, "slow");

	//スクロールをするたびに実行
	$("#ebase6_body").scroll(function () {
		winTop = $("#ebase6_body").scrollTop();
		$("#ebase6_nav").stop(); //これがないと連続して実行されたときに変な動きになります。
		$("#ebase6_nav").css('position','absolute');
		$("#ebase6_nav").animate({top: winTop + "px"}, "slow");

	});






	/*----------ここから下自分で追加した分----------------*/




	//品物データの登録

	function insertdata() {
		//var sql = prompt("input sql","");

		var name = $('#input1_sample').val();
		var unit = $('#input2_sample').val();
		var cost = $('#input3_sample').val();
		var expdays = $('#input4_sample').val();
		var caution = $('#input5_sample').val();
		//var caution = $('#input6_sample').val();

		$('#dataTable').remove();
		$('#ebase6_pamview').remove();

		var field = document.getElementById("datafield");

		var pamView = document.createElement("div");
		field.appendChild(pamView);
		pamView.className = "ebase6_pamview";
		pamView.id = "ebase6_pamview";
		pamView.style.cssText = 'position:absolute;top:70px';

		var table = document.createElement("table");
		field.appendChild(table);
		table.className = "tablesorter";
		table.id = "dataTable";
		table.style.cssText = 'position:absolute;top:80px';

		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'InsertData', name:name, unit:unit, cost:cost, expDays:expdays, caution:caution}, function(jres){

			pamView.innerHTML="SQL [ " + jres.pams["sql"] + " ]";

			//DOM型で要素をAppendしていく
			var theadElem = document.createElement("thead");
			var trElem = document.createElement("tr");
			table.appendChild(theadElem);
			theadElem.appendChild(trElem);

			var ssSearch = document.getElementById("ss_select");

			for(i=0;i<jres.keys.length;i++){
				//テーブルにカラム名を表示
				var col = jres.keys[i];
				var thElem = document.createElement("th");
				trElem.appendChild(thElem);
				thElem.className = jres.tblColData[col]["classname"];
				thElem.innerHTML=jres.tblColData[col]["name"];
			}

			//データ行を作成
			var tbodyElem = document.createElement("tbody");
			table.appendChild(tbodyElem);

			//データのヒットがない場合、空行を作成
			if(jres.tblData.length==0){
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
				}
			}

			for(j=0;j<jres.tblData.length;j++){ //データの書きだし
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
					tdElem.style.background = "#fff";
					var col = jres.keys[i];
					tdElem.innerHTML = jres.tblData[j][col];
				}
			}


			$("#dataTable").tablesorter({
				widgets: ['zebra'],
				sortList: [[0, 1]]
			});
			$("#dataTable").trigger("update");


			$('#new_sample').remove();
			$('#view1_sample').remove();
	        $('#view2_sample').remove();
	        $('#view3_sample').remove();
	        $('#view4_sample').remove();
	        $('#view5_sample').remove();
	        //$('#view6_sample').remove();
	        $('#input1_sample').remove();
	        $('#input2_sample').remove();
	        $('#input3_sample').remove();
	        $('#input4_sample').remove();
	        $('#input5_sample').remove();
	        //$('#input6_sample').remove();
	        $('#button_sample').remove();

			//$.ajaxSetup({ async: true }); //同期の解除
			return false;
		});
	}

	/* 新規登録ボタン */

	function newgoods() {
		$('#dataTable').remove();
		 $('#view1_sample').remove();
	        $('#view2_sample').remove();
	        $('#view3_sample').remove();
	        $('#view4_sample').remove();
	        $('#view5_sample').remove();
	        //$('#view6_sample').remove();
	        $('#input1_sample').remove();
	        $('#input2_sample').remove();
	        $('#input3_sample').remove();
	        $('#input4_sample').remove();
	        $('#input5_sample').remove();
	        //$('#input6_sample').remove();
	        $('#button_sample').remove();

	        var field = document.getElementById("datafield");

	        var view1 = document.createElement("div");
	        field.appendChild(view1);
	        view1.innerHTML = "品名";
	        view1.style.cssText = 'position:absolute;top:150px;';
	        view1.id = "view1_sample";

	        var view2 = document.createElement("div");
	        field.appendChild(view2);
	        view2.innerHTML = "単位";
	        view2.style.cssText = 'position:absolute;top:150px;left:160px;';
	        view2.id = "view2_sample";

	        var view3 = document.createElement("div");
	        field.appendChild(view3);
	        view3.innerHTML = "原価";
	        view3.style.cssText = 'position:absolute;top:150px;left:320px;';
	        view3.id = "view3_sample";

	        var view4 = document.createElement("div");
	        field.appendChild(view4);
	        view4.innerHTML = "賞味期限";
	        view4.style.cssText = 'position:absolute;top:150px;left:480px;';
	        view4.id = "view4_sample";

	        var view5 = document.createElement("div");
	        field.appendChild(view5);
	        view5.innerHTML = "発注の際の注意点";
	        view5.style.cssText = 'position:absolute;top:150px;left:640px;';
	        view5.id = "view5_sample";

	        var btn = document.createElement("input");
	        field.appendChild(btn);
			btn.setAttribute('type',"button");
			btn.setAttribute('value',messages['BTNSUBMIT']);
			btn.setAttribute('id',"button_sample");
			btn.style.cssText = 'position:absolute;top:170px;right:250px';
			$('#button_sample').off("click");
			$('#button_sample').on("click" , insertdata );

			var input1 = document.createElement("input");
	        field.appendChild(input1);
	        input1.setAttribute("type", "text");
	        input1.style.cssText = 'position:absolute;top:170px;width:150px;height:25px';
	        input1.setAttribute("id" ,"input1_sample");

	        var input2 = document.createElement("input");
	        field.appendChild(input2);
	        input2.setAttribute("type", "text");
	        input2.style.cssText = 'position:absolute;top:170px;left:160px;width:150px;height:25px';
	        input2.setAttribute("id" ,"input2_sample");

	        var input3 = document.createElement("input");
	        field.appendChild(input3);
	        input3.setAttribute("type", "text");
	        input3.style.cssText = 'position:absolute;top:170px;left:320px;height:25px;width:150px';
	        input3.setAttribute("id" ,"input3_sample");

	        var input4 = document.createElement("input");
	        field.appendChild(input4);
	        input4.setAttribute("type", "text");
	        input4.style.cssText = 'position:absolute;top:170px;left:480px;height:25px;width:150px';
	        input4.setAttribute("id" ,"input4_sample");

	        var input5 = document.createElement("input");
	        field.appendChild(input5);
	        input5.setAttribute("type", "text");
	        input5.style.cssText = 'position:absolute;top:170px;left:640px;height:25px;width:300px';
	        input5.setAttribute("id" ,"input5_sample");

	        field.update();

	        $('#ebase6_shadow').css('display', 'block');
	}

	//品物一覧表示

	function itemMain() {
		//var sql = prompt("input sql","");
		var list = $('#ebase6_popup_item').val();

		$('#dataTable').remove();
		$('#ebase6_pamview').remove();

		var field = document.getElementById("datafield");

		var pamView = document.createElement("div");
		field.appendChild(pamView);
		pamView.className = "ebase6_pamview";
		pamView.id = "ebase6_pamview";
		pamView.style.cssText = 'position:absolute;top:150px;';

		var table = document.createElement("table");
		field.appendChild(table);
		table.className = "tablesorter";
		table.id = "dataTable";
		table.style.cssText = 'position:absolute;top:165px;';

		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'GoodsList',list:list }, function(jres){

			//pamView.innerHTML="SQL [ " + jres.pams["sql"] + " ]";

			//DOM型で要素をAppendしていく
			var theadElem = document.createElement("thead");
			var trElem = document.createElement("tr");
			table.appendChild(theadElem);
			theadElem.appendChild(trElem);

			var ssSearch = document.getElementById("ss_select");

			for(i=0;i<jres.keys.length;i++){
				//テーブルにカラム名を表示
				var col = jres.keys[i];
				var thElem = document.createElement("th");
				trElem.appendChild(thElem);
				thElem.className = jres.tblColData[col]["classname"];
				thElem.innerHTML=jres.tblColData[col]["name"];
			}

			//データ行を作成
			var tbodyElem = document.createElement("tbody");
			table.appendChild(tbodyElem);

			//データのヒットがない場合、空行を作成
			if(jres.tblData.length==0){
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
				}
			}

			for(j=0;j<jres.tblData.length;j++){ //データの書きだし
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
					tdElem.style.background = "#fff";
					var col = jres.keys[i];
					tdElem.innerHTML = jres.tblData[j][col];
				}
			}


			$("#dataTable").tablesorter({
				widgets: ['zebra'],
				sortList: [[0, 1]]
			});
			$("#dataTable").trigger("update");

			//$.ajaxSetup({ async: true }); //同期の解除
			return false;
		});
	}

	/* 品物一覧ボタン */


	$('a[id=goodsView]').click(function(){
		$('#datafield').empty();
    document.getElementById("ebase6_submenu").innerHTML="品物一覧";

    $('#ebase6_popup').css('width', '300');
	$('#ebase6_popup').css('height', '200');
	$('#ebase6_popup').css('margin', '-150px 0 0 -100px');
	$('#ebase6_shadow').css('display', 'block'); //他入力欄をシャドウ化
	$('#ebase6_popup').css('display', 'block'); //ポップアップ表示
	$('#ebase6_popup_title').html(messages['TOPDBEXE']); //ポップアップにメッセージ表示
	$('#ebase6_popup_body').empty(); //ボディ初期化
	$('#ebase6_popup_foot').empty(); //フッター初期化
	//実行ボタン作成
	var btn = document.createElement("input");
	btn.setAttribute('type',"button");
	btn.setAttribute('value',messages['BTNSUBMIT']);
	btn.setAttribute('id',"ebase6_popup_submit");
	$('#ebase6_popup_foot').append(btn);
	$('#ebase6_popup_submit').off("click"); //実行ボタンの処理を初期化
	$('#ebase6_popup_submit').on("click" , itemMain ); //実行ボタンの処理変更
	$('#ebase6_popup_submit').on("click" , menuClose ); //ポップアップを閉じる
	//入力欄作成
	var inp = document.createElement("textarea");
	inp.setAttribute('id',"ebase6_popup_item");
	inp.style.cssText = 'position:absolute;left:0;width:295px;height:128px;';
	$('#ebase6_popup_body').append(inp);

    var field = document.getElementById("datafield");

	 var btn = document.createElement("input");
     field.appendChild(btn);
		btn.setAttribute('type',"button");
		btn.setAttribute('value',"新規登録");
		btn.setAttribute('id',"new_sample");
		btn.style.cssText = 'font-size:1.4em;padding: 10px 30px;background-color: #FFCCFF;position:absolute;top:80px;'
		$('#new_sample').off("click");
		$('#new_sample').on("click" , newgoods );

		field.update();
	});

	//セレクトボックス変更による処理

	function selectChange() {
		//var sql = prompt("input sql","");
		var list = $('#ebase6_popup_item').val();

		$('#dataTable').remove();
		$('#ebase6_pamview').remove();

		var field = document.getElementById("datafield");

		var table = document.createElement("table");
		field.appendChild(table);
		table.className = "tablesorter";
		table.id = "dataTable";
		table.style.cssText = 'position:absolute;top:160px;width:500px;height:25px;left:160px;';

		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'GoodsList',list:list }, function(jres){

			//DOM型で要素をAppendしていく
			var theadElem = document.createElement("thead");
			var trElem = document.createElement("tr");
			table.appendChild(theadElem);
			theadElem.appendChild(trElem);

			var ssSearch = document.getElementById("ss_select");

			/*for(i=0;i<jres.keys.length;i++){
				//テーブルにカラム名を表示
				var col = jres.keys[i];
				var thElem = document.createElement("th");
				trElem.appendChild(thElem);
				thElem.innerHTML=jres.tblColData[col]["name"];
			}*/

			//データ行を作成
			var tbodyElem = document.createElement("tbody");
			table.appendChild(tbodyElem);

			//データのヒットがない場合、空行を作成
			/*if(jres.tblData.length==0){
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
				}
			}*/

			for(j=0;j<jres.tblData.length;j++){ //データの書きだし
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
					tdElem.style.background = "#fff";
					var col = jres.keys[i];
					tdElem.innerHTML = jres.tblData[j][col];
				}
			}


			$("#dataTable").tablesorter({
				widgets: ['zebra'],
				sortList: [[0, 1]]
			});
			$("#dataTable").trigger("update");

			//$.ajaxSetup({ async: true }); //同期の解除
			return false;
		});
	}

	/* 発注商品の選択 */

	function orderingWork(){
		//var sql = prompt("input sql","");
		var order = $('#ebase6_popup_order').val();

		$('#dataTable').remove();
		$('#ebase6_pamview').remove();

		var field = document.getElementById("datafield");

		/*var pamView = document.createElement("div");
		field.appendChild(pamView);
		pamView.className = "ebase6_pamview";
		pamView.id = "ebase6_pamview";
		pamView.style.cssText = 'position:absolute;top:80px;'*/

		var select = document.createElement("select");
		field.appendChild(select);
		select.id = "dataSelect";
		select.style.cssText = 'position:absolute;top:170px;width:150px;height:30px';
		select.onchange = function(){
			$('#ebase6_popup').css('width', '300');
			$('#ebase6_popup').css('height', '200');
			$('#ebase6_popup').css('margin', '-150px 0 0 -100px');
			$('#ebase6_shadow').css('display', 'block'); //他入力欄をシャドウ化
			$('#ebase6_popup').css('display', 'block'); //ポップアップ表示
			$('#ebase6_popup_title').html(messages['TOPDBEXE']); //ポップアップにメッセージ表示
			$('#ebase6_popup_body').empty(); //ボディ初期化
			$('#ebase6_popup_foot').empty(); //フッター初期化
			//実行ボタン作成
			var btn = document.createElement("input");
			btn.setAttribute('type',"button");
			btn.setAttribute('value',messages['BTNSUBMIT']);
			btn.setAttribute('id',"ebase6_popup_submit");
			$('#ebase6_popup_foot').append(btn);
			$('#ebase6_popup_submit').off("click"); //実行ボタンの処理を初期化
			$('#ebase6_popup_submit').on("click" , selectChange ); //実行ボタンの処理変更
			$('#ebase6_popup_submit').on("click" , menuClose ); //ポップアップを閉じる
			//入力欄作成
			var inp = document.createElement("textarea");
			inp.setAttribute('id',"ebase6_popup_item");
			inp.style.cssText = 'position:absolute;left:0;width:295px;height:128px;';
			$('#ebase6_popup_body').append(inp);

			var view1 = document.createElement("div");
	        field.appendChild(view1);
	        view1.innerHTML = "発注数";
	        view1.style.cssText = 'position:absolute;top:150px;;left:670px;';
	        view1.id = "view1_sample";

	        var view2 = document.createElement("div");
	        field.appendChild(view2);
	        view2.innerHTML = "仕入れ予定日";
	        view2.style.cssText = 'position:absolute;top:150px;right:350px;';
	        view2.id = "view2_sample";

	        var input1 = document.createElement("input");
	        field.appendChild(input1);
	        input1.setAttribute("type", "text");
	        input1.style.cssText = 'position:absolute;top:170px;left:670px;width:150px;height:25px';
	        input1.setAttribute("id" ,"supply1_sample");

	        var input2 = document.createElement("input");
	        field.appendChild(input2);
	        input2.setAttribute("type", "text");
	        input2.style.cssText = 'position:absolute;top:170px;right:300px;width:150px;height:25px';
	        input2.setAttribute("id" ,"supply2_sample");
		};

		//SQL文：単位、原価、前日発注数、在庫数表示
		//select UNIT,COST,ORDER_NUM,STOCK from ITEM_MST i inner join SUPPLY_HISTORY su on i.id = su.id inner join STOCK_HISTORY so on i.id = so.id where i.id = 2 and su.SUPPLY_DAY = ADDDATE(CURRENT_DATE(), -1);


		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'OrderWork', order:order}, function(jres){

			//pamView.innerHTML="SQL [ " + jres.pams["sql"] + " ]";

			var option1 = document.createElement("option");
			option1.innerHTML = "選択してください"
			select.appendChild(option1);

			for(i=0;i<jres.tblData.length;i++){
                var option2 = document.createElement("option");
                option2.value = jres.tblData[i]["id"];
                option2.innerHTML = jres.tblData[i]["ITEM_NAME"];
                option2.setAttribute('id',"option_data");
                select.appendChild(option2);
            }

			$("#dataSelect").trigger("update");

			//$.ajaxSetup({ async: true }); //同期の解除
			return false;
		});
	}

	//発注データ登録

	function supplydata() {

		var id = $('#dataSelect').val();
		var ord = $('#supply1_sample').val();
		var scheduled = $('#supply2_sample').val();

		$('#dataTable').remove();
		$('#ebase6_pamview').remove();

		var field = document.getElementById("datafield");

		var pamView = document.createElement("div");
		field.appendChild(pamView);
		pamView.className = "ebase6_pamview";
		pamView.id = "ebase6_pamview";
		pamView.style.cssText = 'position:absolute;top:70px';

		var table = document.createElement("table");
		field.appendChild(table);
		table.className = "tablesorter";
		table.id = "dataTable";
		table.style.cssText = 'position:absolute;top:80px';

		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'SupplyData',id:id, ord:ord, scheduled:scheduled }, function(jres){

			pamView.innerHTML="SQL [ " + jres.pams["sql"] + " ]";

			//DOM型で要素をAppendしていく
			var theadElem = document.createElement("thead");
			var trElem = document.createElement("tr");
			table.appendChild(theadElem);
			theadElem.appendChild(trElem);

			var ssSearch = document.getElementById("ss_select");

			for(i=0;i<jres.keys.length;i++){
				//テーブルにカラム名を表示
				var col = jres.keys[i];
				var thElem = document.createElement("th");
				trElem.appendChild(thElem);
				thElem.className = jres.tblColData[col]["classname"];
				thElem.innerHTML=jres.tblColData[col]["name"];
			}

			//データ行を作成
			var tbodyElem = document.createElement("tbody");
			table.appendChild(tbodyElem);

			//データのヒットがない場合、空行を作成
			if(jres.tblData.length==0){
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
				}
			}

			for(j=0;j<jres.tblData.length;j++){ //データの書きだし
				var trElem = document.createElement("tr");
				tbodyElem.appendChild(trElem);
				for(i=0;i<jres.keys.length;i++){
					var tdElem = document.createElement("td");
					trElem.appendChild(tdElem);
					tdElem.style.background = "#fff";
					var col = jres.keys[i];
					tdElem.innerHTML = jres.tblData[j][col];
				}
			}


			$("#dataTable").tablesorter({
				widgets: ['zebra'],
				sortList: [[0, 1]]
			});
			$("#dataTable").trigger("update");

			$('#button_sample').remove();
			$('#dataSelect').remove();
			$('#view1_sample').remove();
			$('#view2_sample').remove();
			$('#supply1_sample').remove();
			$('#supply2_sample').remove();

			//$.ajaxSetup({ async: true }); //同期の解除
			return false;
		});
	}

	/* 発注作業ボタン */

	$('a[id=orderingWork]').click(function(){
		$('#datafield').empty();

		document.getElementById("ebase6_submenu").innerHTML="発注作業";
		$('#ebase6_popup').css('width', '300');
		$('#ebase6_popup').css('height', '200');
		$('#ebase6_popup').css('margin', '-150px 0 0 -100px');
		$('#ebase6_shadow').css('display', 'block'); //他入力欄をシャドウ化
		$('#ebase6_popup').css('display', 'block'); //ポップアップ表示
		$('#ebase6_popup_title').html(messages['TOPDBEXE']); //ポップアップにメッセージ表示
		$('#ebase6_popup_body').empty(); //ボディ初期化
		$('#ebase6_popup_foot').empty(); //フッター初期化
		//実行ボタン作成
		var btn = document.createElement("input");
		btn.setAttribute('type',"button");
		btn.setAttribute('value',messages['BTNSUBMIT']);
		btn.setAttribute('id',"ebase6_popup_submit");
		$('#ebase6_popup_foot').append(btn);
		$('#ebase6_popup_submit').off("click"); //実行ボタンの処理を初期化
		$('#ebase6_popup_submit').on("click" , orderingWork ); //実行ボタンの処理変更
		$('#ebase6_popup_submit').on("click" , menuClose );//ポップアップを閉じる
		//入力欄作成
		var inp = document.createElement("textarea");
		inp.setAttribute('id',"ebase6_popup_order");
		inp.style.cssText = 'position:absolute;left:0;width:295px;height:128px;';
		$('#ebase6_popup_body').append(inp);

		var field = document.getElementById("datafield");

        var btn = document.createElement("input");
        field.appendChild(btn);
		btn.setAttribute('type',"button");
		btn.setAttribute('value', "確定");
		btn.setAttribute('id',"button_sample");
		btn.style.cssText = 'font-size:2.0em;padding: 5px 10px;font-weight: bold;background-color: #3399CC;position:absolute;top:80px;left:100px'
		$('#button_sample').off("click");
		$('#button_sample').on("click" , supplydata );


        field.update();
	});


	// 不足数表示

	function orderNumber() {
		//var sql = prompt("input sql","");
		var ordNum = $('#order_number').val();

		//$('#dataTable').remove();

		var field = document.getElementById("datafield");

		var view = document.createElement("div");
        field.appendChild(view);
        view.innerHTML = "不足数";
        view.style.cssText = 'position:absolute;top:150px;right:410px;';
        view.setAttribute('id',"view_sample");

        var input = document.createElement("input");
        field.appendChild(input);
        input.setAttribute("type", "text");
        input.style.cssText = 'position:absolute;top:170px;width:150px;height:25px;right:300px';
        input.setAttribute('id',"input_sample");

		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'OrderNumber',ordNum:ordNum}, function(jres){

			//pamView.innerHTML="SQL [ " + jres.pams["sql"] + " ]";

			for(i=0;i<jres.tblData.length;i++){
			input.value = jres.tblData[i]["ORDER_NUM"] - $('#input1_sample').val();
			input.innerHTML = jres.tblData[i]["ORDER_NUM"] - $('#input1_sample').val();
			}

            if (input.value > 0) {
            	input.style.cssText = 'position:absolute;top:170px;width:150px;height:25px;right:300px;color:#FF0000';
            } else if (input.value == 0) {
            	input.style.cssText = 'position:absolute;top:170px;width:150px;height:25px;right:300px;color:#000000';
            }

		});
	}

	/* 検品商品表示 */

	function goodsCheck(){
		var check = $('#ebase6_popup_check').val();

		$('#dataTable').remove();
		$('#ebase6_pamview').remove();

		var field = document.getElementById("datafield");

		var select = document.createElement("select");
		field.appendChild(select);
		select.id = "dataSelect";
		select.style.cssText = 'position:absolute;top:170px;width:150px;height:30px';
		select.onchange = function(){
			$('#view1_sample').remove();
			$('#input1_sample').remove();
			$('#ebase6_popup').css('width', '300');
			$('#ebase6_popup').css('height', '200');
			$('#ebase6_popup').css('margin', '-150px 0 0 -100px');
			$('#ebase6_shadow').css('display', 'block'); //他入力欄をシャドウ化
			$('#ebase6_popup').css('display', 'block'); //ポップアップ表示
			$('#ebase6_popup_title').html(messages['TOPDBEXE']); //ポップアップにメッセージ表示
			$('#ebase6_popup_body').empty(); //ボディ初期化
			$('#ebase6_popup_foot').empty(); //フッター初期化
			//実行ボタン作成
			var btn = document.createElement("input");
			btn.setAttribute('type',"button");
			btn.setAttribute('value',messages['BTNSUBMIT']);
			btn.setAttribute('id',"ebase6_popup_submit");
			$('#ebase6_popup_foot').append(btn);
			$('#ebase6_popup_submit').off("click"); //実行ボタンの処理を初期化
			$('#ebase6_popup_submit').on("click" , selectChange ); //実行ボタンの処理変更
			$('#ebase6_popup_submit').on("click" , menuClose ); //ポップアップを閉じる
			//入力欄作成
			var inp = document.createElement("textarea");
			inp.setAttribute('id',"ebase6_popup_item");
			inp.style.cssText = 'position:absolute;left:0;width:295px;height:128px;';
			$('#ebase6_popup_body').append(inp);

			var view1 = document.createElement("div");
	        field.appendChild(view1);
	        view1.innerHTML = "仕入数";
	        view1.style.cssText = 'position:absolute;top:150px;left:670px;';
	        view1.id = "view1_sample";

	        var input1 = document.createElement("input");
	        field.appendChild(input1);
	        input1.setAttribute("type", "text");
	        input1.style.cssText = 'position:absolute;top:170px;width:150px;height:25px;left:670px';
	        input1.setAttribute("id" ,"input1_sample");
	        input1.onchange = function() {
	        	$('#ebase6_popup').css('width', '300');
	    		$('#ebase6_popup').css('height', '200');
	    		$('#ebase6_popup').css('margin', '-150px 0 0 -100px');
	    		$('#ebase6_shadow').css('display', 'block'); //他入力欄をシャドウ化
	    		$('#ebase6_popup').css('display', 'block'); //ポップアップ表示
	    		$('#ebase6_popup_title').html(messages['TOPDBEXE']); //ポップアップにメッセージ表示
	    		$('#ebase6_popup_body').empty(); //ボディ初期化
	    		$('#ebase6_popup_foot').empty(); //フッター初期化
	    		//実行ボタン作成
	    		var btn = document.createElement("input");
	    		btn.setAttribute('type',"button");
	    		btn.setAttribute('value',messages['BTNSUBMIT']);
	    		btn.setAttribute('id',"ebase6_popup_submit");
	    		$('#ebase6_popup_foot').append(btn);
	    		$('#ebase6_popup_submit').off("click"); //実行ボタンの処理を初期化
	    		$('#ebase6_popup_submit').on("click" , orderNumber ); //実行ボタンの処理変更
	    		$('#ebase6_popup_submit').on("click" , menuClose ); //ポップアップを閉じる
	    		//入力欄作成
	    		var inp = document.createElement("textarea");
	    		inp.setAttribute('id',"order_number");
	    		inp.style.cssText = 'position:absolute;left:0;width:295px;height:128px;';
	    		$('#ebase6_popup_body').append(inp);

	            field.update();

	    	}

		}

		//submit処理開始
		//$.ajaxSetup({ async: false }); //同期
		$.postJSON("DQube",{actionID:'GoodsCheck', check:check}, function(jres){

			var option1 = document.createElement("option");
			option1.innerHTML = "選択してください"
			select.appendChild(option1);

			for(i=0;i<jres.tblData.length;i++){
                var option2 = document.createElement("option");
                option2.value = jres.tblData[i]["id"];
                option2.innerHTML = jres.tblData[i]["ITEM_NAME"];
                option2.setAttribute('id',"option_data");
                select.appendChild(option2);
            }

			$("#dataselect").trigger("update");

			//$.ajaxSetup({ async: true }); //同期の解除
			return false;
		});
	}

	//SQL文：単位、原価、発注日、発注数表示
	//select UNIT,COST,SUPPLY_DAY,ORDER_NUM from ITEM_MST i inner join SUPPLY_HISTORY su on i.id = su.id where i.id = 2;

	//検品データ登録

	function checkRegister() {

	var id = $('#dataSelect').val();
	var supply = $('#input1_sample').val();

	$('#dataTable').remove();
	$('#ebase6_pamview').remove();

	var field = document.getElementById("datafield");

	var pamView = document.createElement("div");
	field.appendChild(pamView);
	pamView.className = "ebase6_pamview";
	pamView.id = "ebase6_pamview";
	pamView.style.cssText = 'position:absolute;top:70px';

	var table = document.createElement("table");
	field.appendChild(table);
	table.className = "tablesorter";
	table.id = "dataTable";
	table.style.cssText = 'position:absolute;top:80px';

	//submit処理開始
	//$.ajaxSetup({ async: false }); //同期
	$.postJSON("DQube",{actionID:'CheckRegister',id:id, supply:supply }, function(jres){

		pamView.innerHTML="SQL [ " + jres.pams["sql"] + " ]";

		//DOM型で要素をAppendしていく
		var theadElem = document.createElement("thead");
		var trElem = document.createElement("tr");
		table.appendChild(theadElem);
		theadElem.appendChild(trElem);

		var ssSearch = document.getElementById("ss_select");

		for(i=0;i<jres.keys.length;i++){
			//テーブルにカラム名を表示
			var col = jres.keys[i];
			var thElem = document.createElement("th");
			trElem.appendChild(thElem);
			thElem.className = jres.tblColData[col]["classname"];
			thElem.innerHTML=jres.tblColData[col]["name"];
		}

		//データ行を作成
		var tbodyElem = document.createElement("tbody");
		table.appendChild(tbodyElem);

		//データのヒットがない場合、空行を作成
		if(jres.tblData.length==0){
			var trElem = document.createElement("tr");
			tbodyElem.appendChild(trElem);
			for(i=0;i<jres.keys.length;i++){
				var tdElem = document.createElement("td");
				trElem.appendChild(tdElem);
			}
		}

		for(j=0;j<jres.tblData.length;j++){ //データの書きだし
			var trElem = document.createElement("tr");
			tbodyElem.appendChild(trElem);
			for(i=0;i<jres.keys.length;i++){
				var tdElem = document.createElement("td");
				trElem.appendChild(tdElem);
				tdElem.style.background = "#fff";
				var col = jres.keys[i];
				tdElem.innerHTML = jres.tblData[j][col];
			}
		}


		$("#dataTable").tablesorter({
			widgets: ['zebra'],
			sortList: [[0, 1]]
		});
		$("#dataTable").trigger("update");
		$('#new_sample').remove();
		$('#view_sample').remove();
		$('#input_sample').remove();
		$('#view1_sample').remove();
		$('#input1_sample').remove();
		$('#dataSelect').remove();

		//$.ajaxSetup({ async: true }); //同期の解除
		return false;f
	});
}

	/* 品物検品ボタン */

	$('a[id=goodsCheck]').click(function(){
		$('#datafield').empty();
		document.getElementById("ebase6_submenu").innerHTML="品物検品";
		$('#ebase6_popup').css('width', '300');
		$('#ebase6_popup').css('height', '200');
		$('#ebase6_popup').css('margin', '-150px 0 0 -100px');
		$('#ebase6_shadow').css('display', 'block'); //他入力欄をシャドウ化
		$('#ebase6_popup').css('display', 'block'); //ポップアップ表示
		$('#ebase6_popup_title').html(messages['TOPDBEXE']); //ポップアップにメッセージ表示
		$('#ebase6_popup_body').empty(); //ボディ初期化
		$('#ebase6_popup_foot').empty(); //フッター初期化
		//実行ボタン作成
		var btn = document.createElement("input");
		btn.setAttribute('type',"button");
		btn.setAttribute('value',messages['BTNSUBMIT']);
		btn.setAttribute('id',"ebase6_popup_submit");
		$('#ebase6_popup_foot').append(btn);
		$('#ebase6_popup_submit').off("click"); //実行ボタンの処理を初期化
		$('#ebase6_popup_submit').on("click" , goodsCheck ); //実行ボタンの処理変更
		$('#ebase6_popup_submit').on("click" , menuClose );//ポップアップを閉じる
		//入力欄作成
		var inp = document.createElement("textarea");
		inp.setAttribute('id',"ebase6_popup_check");
		inp.style.cssText = 'position:absolute;left:0;width:295px;height:128px;';
		$('#ebase6_popup_body').append(inp);

		var field = document.getElementById("datafield");

		 var btn = document.createElement("input");
	     field.appendChild(btn);
			btn.setAttribute('type',"button");
			btn.setAttribute('value',"登録");
			btn.setAttribute('id',"new_sample");
			btn.style.cssText = 'font-size:1.4em;padding: 10px 30px;background-color: #FF6633;position:absolute;top:80px;left:70px'
			$('#new_sample').off("click");
			$('#new_sample').on("click" , checkRegister );


			field.update();
	});


	/* 作業項目 */


	$('a[id=stockManegement]').click(function(){
		$('#datafield').empty();
		document.getElementById("ebase6_submenu").innerHTML="在庫管理";});

	$('a[id=Inventories]').click(function(){
		$('#datafield').empty();
		document.getElementById("ebase6_submenu").innerHTML="棚卸作業";});

	$('a[id=History]').click(function(){
		$('#datafield').empty();
		document.getElementById("ebase6_submenu").innerHTML="履歴表示";});
});
