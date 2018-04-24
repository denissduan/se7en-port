[%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import org.apache.commons.lang.StringUtils
import StringServices
import ListServices
import VariableServices
%]

[%script type="Operation" name="searchFormInit" post="trim()"%]
	[%if (name.lowerCase().contains("search")){%]
		[%if (ownedParameter.size() > 0){%]
<div class="page-header">		
	<form id="searchForm" action="[%get("varName")%]/query.do" method="post" class="form-inline" role="form">		
				[%for (ownedParameter){%]
					[%push()%]
						[%for (get("attrs")){%]
							[%put("prop")%]
							[%if (peek().name == name || peek().name.indexOf(name) == 0){%]
								[%if (type.filter("PrimitiveType") != null){%]
									[%if (type.name.lowerCase().contains("string")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="form-control input-sm" placeholder="">
	  	</div>
									[%}else if(type.name.lowerCase().contains("int")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="input-mini" placeholder="" />
	    	<script type="text/javascript">
	    		$('#[%name%]').ace_spinner({value:null,min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralInteger").value != null){%][%ownedElement.filter("LiteralInteger").value * 10 - 1%][%}else{%]4294967295[%}%],step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
	  	</div>
									[%}else if(type.name.lowerCase().contains("boolean")){%]
		<div class="checkbox">
		    <label>
		      <input type="checkbox" id="[%name.firstLower()%]" name="[%name.firstLower()%]" value="0" onclick="$.core.checkClick(this)"> [%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]
		    </label>
	  	</div>
									[%}else if(type.name.lowerCase().contains("date")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="form-control input-sm date-picker" data-date-format="yyyy-mm-dd">
			<span class="input-group-addon">
				<i class="fa fa-calendar bigger-110"></i>
			</span>
	  	</div>
									[%}else if(type.name.lowerCase().contains("datetime")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="form-control input-sm datetime-picker" >
			<span class="input-group-addon">
				<i class="fa fa-clock-o bigger-110"></i>
			</span>
	  	</div>
									[%}else if(type.name.lowerCase().contains("byte")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="input-mini" placeholder="" />
	    	<script type="text/javascript">
	    		$('#[%name%]').ace_spinner({value:null,min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralInteger").value != null){%][%ownedElement.filter("LiteralInteger").value * 10 - 1%][%}else{%]255[%}%],step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
	  	</div>
									[%}else if(type.name.lowerCase().contains("char")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="form-control input-sm" placeholder="">
	  	</div>
									[%}else if(type.name.lowerCase().contains("short")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="input-mini" placeholder="" />
	    	<script type="text/javascript">
	    		$('#[%name%]').ace_spinner({value:null,min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralInteger").value != null){%][%ownedElement.filter("LiteralInteger").value * 10 - 1%][%}else{%]65535[%}%],step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
	  	</div>
									[%}else if(type.name.lowerCase().contains("long")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="input-mini" placeholder="" />
	    	<script type="text/javascript">
	    		$('#[%name%]').ace_spinner({value:null,min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralInteger").value != null){%][%ownedElement.filter("LiteralInteger").value * 10 - 1%][%}else{%]4294967295[%}%],step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
	  	</div>
									[%}else if(type.name.lowerCase().contains("float")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="input-mini" placeholder="" />
	    	<script type="text/javascript">
	    		$('#[%name%]').ace_spinner({value:null,min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralInteger").value != null){%][%ownedElement.filter("LiteralInteger").value * 10 - 1%][%}else{%]65535[%}%],step:0.1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
	  	</div>
									[%}else if(type.name.lowerCase().contains("double")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="input-mini" placeholder="" />
	    	<script type="text/javascript">
	    		$('#[%name%]').ace_spinner({value:null,min:[%if (get("prop").lower != null){%][%get("prop").lower%][%}else{%]0[%}%],max:[%if (get("prop").upper != null){%][%get("prop").upper%][%}else if(ownedElement.filter("LiteralInteger").value != null){%][%ownedElement.filter("LiteralInteger").value * 10 - 1%][%}else{%]4294967295[%}%],step:0.1, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
	    	</script>
	  	</div>
									[%}else if(type.name.lowerCase().contains("blob")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="form-control input-sm" placeholder="">
	  	</div>
									[%}else if(type.name.lowerCase().contains("clob")){%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="form-control input-sm" placeholder="">
	  	</div>
									[%}%]
								[%}else if(type.filter("Enumeration") != null){%]
									[%name.lowerCase().push()%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
			<select id="[%name.firstLower()%]" name="[%name.firstLower()%]" size="1" class="form-control input-sm">
				[%for (type.ownedElement){%]
				<option value="[%ownedElement.filter("LiteralInteger").value%]" >[%if (ownedComment != null){%][%ownedComment.body%][%}%]</option>
				[%}%]
			</select>
	  	</div>
									[%pop()%]
								[%}else{%]
		<div class="form-group">
	    	<label class="control-label" for="[%name.firstLower()%]">[%if (get("prop").ownedComment != null){%][%get("prop").ownedComment.body%][%}%]</label>
	    	<input type="text" id="[%name.firstLower()%]" name="[%name.firstLower()%]" class="form-control input-sm" placeholder="">
	  	</div>
								[%}%]
							[%}%]
						[%}%]
					[%pop()%]	
				[%}%]
	</form>
</div>
		[%}%]
	[%}%]					