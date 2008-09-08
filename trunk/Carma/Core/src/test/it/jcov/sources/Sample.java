package sources;

public class Sample {
	
	public int decide(int a) {
		if (a == 
			1) {
			return 7;
		}
		return 3;
	}
}


/**
 * 
StartPos: 6165 EndPos: 6165 StartLine: 6 StartColumn: 21 EndLine: 7 EndColumn: 26 StartPC: 0 EndPC: 1
StartPos: 6165 EndPos: 6165 StartLine: 6 StartColumn: 21 EndLine: 7 EndColumn: 26 StartPC: 2 EndPC: 4
StartPos: 8217 EndPos: 8217 StartLine: 8 StartColumn: 25 EndLine: 9 EndColumn: 0 StartPC: 5 EndPC: 7
StartPos: 7196 EndPos: 7196 StartLine: 7 StartColumn: 28 EndLine: 9 EndColumn: 18 StartPC: 5 EndPC: 7
StartPos: 6161 EndPos: 6161 StartLine: 6 StartColumn: 17 EndLine: 9 EndColumn: 18 StartPC: 0 EndPC: 7
StartPos: 10257 EndPos: 10257 StartLine: 10 StartColumn: 17 EndLine: 11 EndColumn: 0 StartPC: 8 EndPC: 9
StartPos: 5154 EndPos: 5154 StartLine: 5 StartColumn: 34 EndLine: 11 EndColumn: 10 StartPC: 0 EndPC: 9
 */

/**
Tree: a == 1, type: CRT_FLOW_CONTROLLER
Start: pos = 76, pc = 0, line = 6, column = 7, new_pos = 6151
End:   pos = 82, pc = 1, line = 6, column = 13, new_pos = 6157
Tree: a == 1, type: CRT_BRANCH_FALSE
Start: pos = 76, pc = 2, line = 6, column = 7, new_pos = 6151
End:   pos = 82, pc = 4, line = 6, column = 13, new_pos = 6157
Tree: return 7;, type: CRT_STATEMENT
Start: pos = 89, pc = 5, line = 7, column = 4, new_pos = 7172
End:   pos = 98, pc = 7, line = 7, column = 13, new_pos = 7181
Tree: {
    return 7;
}, type: CRT_STATEMENT CRT_BLOCK CRT_FLOW_TARGET
Start: pos = 84, pc = 5, line = 6, column = 15, new_pos = 6159
End:   pos = 101, pc = 7, line = 8, column = 3, new_pos = 8195
Tree: if (a == 1) {
    return 7;
}, type: CRT_STATEMENT
Start: pos = 72, pc = 0, line = 6, column = 3, new_pos = 6147
End:   pos = 101, pc = 7, line = 8, column = 3, new_pos = 8195
Tree: return 3;, type: CRT_STATEMENT
Start: pos = 105, pc = 8, line = 9, column = 3, new_pos = 9219
End:   pos = 114, pc = 9, line = 9, column = 12, new_pos = 9228
Tree: {
    if (a == 1) {
        return 7;
    }
    return 3;
}, type: CRT_BLOCK
Start: pos = 68, pc = 0, line = 5, column = 27, new_pos = 5147
End:   pos = 116, pc = 9, line = 10, column = 2, new_pos = 10242
*/