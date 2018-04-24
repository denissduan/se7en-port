package com.se7en.service.md;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.internal.impl.LiteralUnlimitedNaturalImpl;
import org.springframework.stereotype.Service;
@Service
public class ListServices {

	public int size(final List<?> list){
		
		return list.size();
	}
	
	public boolean isLast(final List<?> list,final int index){
		
		return index == list.size();
	}
	
	public LiteralUnlimitedNaturalImpl filterLiteralUnlimitedNatural(final EList<Element> eles){
		LiteralUnlimitedNaturalImpl ret = null;
		
		for (final Element element : eles) {
			if(element instanceof LiteralUnlimitedNaturalImpl && ((LiteralUnlimitedNaturalImpl) element).getName().contains("length")){
				ret = (LiteralUnlimitedNaturalImpl)element;
			}
		}
		return ret;
	}
}
