[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
import VariableServices
%]

[%script type="Operation" name="operateForm" post="replaceAll("\t+\r\n","").replaceAll("[\r\n]{2,}","\r\n").trim()"%]
		[%if (name.lowerCase().contains("form")){%]
			[%for (ownedParameter){%]
				[%push()%]
					[%for (get("attrs")){%]
						[%put("prop")%]
						[%if (peek().name == name){%]
							[%--原始类型--%]
	    					[%if (type.filter("PrimitiveType") != null){%]
								[%if (type.name.lowerCase().contains("string")){%]
									[%for (ownedElement.filter("LiteralUnlimitedNatural")){%]
										[%if (self().name == "length"){%]
											[%if (self().value != null && self().value > 80){%]
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<textarea id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" class="form-control" placeholder="" maxlength="[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralUnlimitedNatural").value != null){%][%ownedElement.filter("LiteralUnlimitedNatural").value * 10 - 1%][%}else{%]4294967295[%}%]">${[%get("varName")%].[%get("prop").name.firstLower()%]}</textarea>
			</div>
		</div>
											[%}else{%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" class="col-xs-10 col-sm-5" value="${[%get("varName")%].[%get("prop").name.firstLower()%] }" placeholder="">
			</div>
		</div>
											[%}%]
										[%}else{%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" class="col-xs-10 col-sm-5" value="${[%get("varName")%].[%get("prop").name.firstLower()%] }" placeholder="">
			</div>
		</div>
										[%}%]
									[%}%]
								[%}else if(type.name.lowerCase().contains("int")){%]
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" class="input-mini" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" />
			</div>
	    	<script type="text/javascript">
	    		$('#[%name.firstLower()%]').ace_spinner({value:'${[%get("varName")%].[%get("prop").name.firstLower()%]}',min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralUnlimitedNatural").value != null){%][%ownedElement.filter("LiteralUnlimitedNatural").value * 10 - 1%][%}else{%]4294967295[%}%],step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
		</div>
								[%}else if(type.name.lowerCase().contains("boolean")){%]
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="checkbox">
				<label>
					<input type="checkbox" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" ${[%get("varName")%].[%name%] eq "1" ? "checked" : ""} value="${[%get("varName")%].[%get("prop").name.firstLower()%] }" onclick="$.core.checkClick(this)" class="ace">
					<span class="lbl"> [%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</span>
				</label>
			</div>
		</div>
								[%}else if(type.name.lowerCase().contains("date")){%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" value="${[%get("varName")%].[%get("prop").name.firstLower()%] }" class="col-xs-10 col-sm-5 date-picker" data-date-format="yyyy-mm-dd">
				<span class="input-group-addon">
					<i class="fa fa-calendar bigger-110"></i>
				</span>
			</div>
		</div>
								[%}else if(type.name.lowerCase().contains("datetime")){%]
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" value="${[%get("varName")%].[%get("prop").name.firstLower()%] }" class="col-xs-10 col-sm-5 datetime-picker" data-date-format="yyyy-mm-dd">
				<span class="input-group-addon">
					<i class="fa fa-calendar bigger-110"></i>
				</span>
			</div>
		</div>
								[%}else if(type.name.lowerCase().contains("byte")){%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" class="input-mini" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" />
			</div>
	    	<script type="text/javascript">
	    		$('#[%name.firstLower()%]').ace_spinner({value:'${[%get("varName")%].[%get("prop").name.firstLower()%]}',min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralUnlimitedNatural").value != null){%][%ownedElement.filter("LiteralUnlimitedNatural").value * 10 - 1%][%}else{%]256[%}%],step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
		</div>
								[%}else if(type.name.lowerCase().contains("char")){%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" class="col-xs-10 col-sm-5" value="${[%get("varName")%].[%get("prop").name.firstLower()%] }" placeholder="">
			</div>
		</div>
								[%}else if(type.name.lowerCase().contains("short")){%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" class="input-mini" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" />
			</div>
	    	<script type="text/javascript">
	    		$('#[%name.firstLower()%]').ace_spinner({value:'${[%get("varName")%].[%get("prop").name.firstLower()%]}',min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralUnlimitedNatural").value != null){%][%ownedElement.filter("LiteralUnlimitedNatural").value * 10 - 1%][%}else{%]65535[%}%],step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
		</div>
								[%}else if(type.name.lowerCase().contains("long")){%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" class="input-mini" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" />
			</div>
	    	<script type="text/javascript">
	    		$('#[%name.firstLower()%]').ace_spinner({value:'${[%get("varName")%].[%get("prop").name.firstLower()%]}',min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralUnlimitedNatural").value != null){%][%ownedElement.filter("LiteralUnlimitedNatural").value * 10 - 1%][%}else{%]4294967295[%}%],step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
		</div>
								[%}else if(type.name.lowerCase().contains("float")){%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" class="input-mini" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" />
			</div>
	    	<script type="text/javascript">
	    		$('#[%name.firstLower()%]').ace_spinner({value:'${[%get("varName")%].[%get("prop").name.firstLower()%]}',min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralUnlimitedNatural").value != null){%][%ownedElement.filter("LiteralUnlimitedNatural").value * 10 - 1%][%}else{%]65535[%}%],step:0.1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
		</div>
								[%}else if(type.name.lowerCase().contains("double")){%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" class="input-mini" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" />
			</div>
	    	<script type="text/javascript">
	    		$('#[%name.firstLower()%]').ace_spinner({value:'${[%get("varName")%].[%get("prop").name.firstLower()%]}',min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralUnlimitedNatural").value != null){%][%ownedElement.filter("LiteralUnlimitedNatural").value * 10 - 1%][%}else{%]65535[%}%],step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
		</div>
								[%}else if(type.name.lowerCase().contains("blob")){%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<textarea id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" class="form-control" placeholder="" maxlength="[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralUnlimitedNatural").value != null){%][%ownedElement.filter("LiteralUnlimitedNatural").value * 10 - 1%][%}else{%]4294967295[%}%]">${[%get("varName")%].[%get("prop").name.firstLower()%]}</textarea>
			</div>
		</div>
								[%}else if(type.name.lowerCase().contains("clob")){%]
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<textarea id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" class="form-control" placeholder="" maxlength="[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralUnlimitedNatural").value != null){%][%ownedElement.filter("LiteralUnlimitedNatural").value * 10 - 1%][%}else{%]4294967295[%}%]">${[%get("varName")%].[%get("prop").name.firstLower()%]}</textarea>
			</div>
		</div>
								[%}%]
							[%--枚举字段--%]
							[%}else if(type.filter("Enumeration") != null){%]
								[%name.lowerCase().push()%]
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<select id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" size="1">
					[%for (type.ownedElement){%]
					<option value="[%ownedElement.filter("LiteralUnlimitedNatural").value%]" ${[%get("varName")%].[%peek()%] eq "[%ownedElement.filter("LiteralUnlimitedNatural").value%]" ? "selected" : ""}>[%if (ownedComment != null){%][%ownedComment.body%][%}%]</option>
					[%}%]
				</select>
			</div>
		</div>
								[%pop()%]
							[%--关联字段--%]
							[%}else if(type.filter("Class") != null){%]
								[%name.lowerCase().push()%]
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="hidden" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%].id" class="col-xs-10 col-sm-5" value="${[%get("varName")%].[%get("prop").name.firstLower()%].id }" placeholder="">
				<input type="text" id="[%get("prop").name.firstLower()%]_el" class="col-xs-10 col-sm-5" value="${[%get("varName")%].[%get("prop").name.firstLower()%].name }" placeholder="" readonly="readonly">
				<span class="input-group-btn">
					<button class="btn btn-sm btn-light" type="button" onclick='relOpen("[%type.name.firstLower()%]/query.do","[%get("prop").name.firstLower()%]")'>
						<i class="ace-icon fa fa-hand-o-right bigger-110"></i>
					</button>
				</span>
			</div>
		</div>	
								[%pop()%]				
							[%}else{%]
	    <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="[%get("prop").name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<div class="col-sm-9">
				<input type="text" id="[%get("prop").name.firstLower()%]" name="[%get("prop").name.firstLower()%]" class="col-xs-10 col-sm-5" value="${[%get("varName")%].[%get("prop").name.firstLower()%] }" placeholder="">
			</div>
		</div>
							[%}%]	
						[%}%]
					[%}%]
				[%pop()%]	
			[%}%]
		[%}%]