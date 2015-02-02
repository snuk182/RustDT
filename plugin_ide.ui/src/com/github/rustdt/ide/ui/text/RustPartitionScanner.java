/*******************************************************************************
 * Copyright (c) 2015, 2015 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package com.github.rustdt.ide.ui.text;

import melnorme.lang.ide.ui.TextSettings_Actual.LangPartitionTypes;
import melnorme.lang.ide.ui.text.PatternRule_Fixed;
import melnorme.utilbox.collections.ArrayList2;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class RustPartitionScanner extends RuleBasedPartitionScanner {
	
	private static final char NO_ESCAPE_CHAR = (char) -1;
	
	public RustPartitionScanner() {
		IToken tkComment = new Token(LangPartitionTypes.COMMENT.getId());
		IToken tkDocComment = new Token(LangPartitionTypes.DOC_COMMENT.getId());
		IToken tkString = new Token(LangPartitionTypes.STRING.getId());
		IToken tkCharacter = new Token(LangPartitionTypes.CHARACTER.getId());
		
		ArrayList2<IPredicateRule> rules = new ArrayList2<>();
		
		rules.add(new PatternRule_Fixed("///", null, tkDocComment, NO_ESCAPE_CHAR, true, true));
		rules.add(new PatternRule_Fixed("/**", "*/", tkDocComment, NO_ESCAPE_CHAR, false, true));
		rules.add(new PatternRule_Fixed("//", null, tkComment, NO_ESCAPE_CHAR, true, true));
		rules.add(new PatternRule_Fixed("/*", "*/", tkComment, NO_ESCAPE_CHAR, false, true));
		
		
		rules.add(new PatternRule_Fixed("'", "'", tkCharacter, '\\', true, true));
		rules.add(new PatternRule_Fixed("b'", "'", tkCharacter, '\\', true, true));
		rules.add(new PatternRule_Fixed("\"", "\"", tkString, '\\', true, true));
		rules.add(new PatternRule_Fixed("b\"", "\"", tkString, '\\', true, true));
		rules.add(new PatternRule_Fixed("r##\"", "\"", tkString, NO_ESCAPE_CHAR, true, true));
		rules.add(new PatternRule_Fixed("br##\"", "\"", tkString, NO_ESCAPE_CHAR, true, true));
		
		setPredicateRules(rules.toArray(IPredicateRule.class));
	}
	
}