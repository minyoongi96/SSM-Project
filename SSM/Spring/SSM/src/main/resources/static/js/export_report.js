/**
 * 
 */
 
function s2ab(s) {
	var buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
	var view = new Uint8Array(buf);  //create uint8array as viewer
	for (var i=0; i<s.length; i++) {
		view[i] = s.charCodeAt(i) & 0xFF; //convert to octet
	}
	return buf;
}

function exportExcel(){
	var workBook = XLSX.utils.book_new();
	//var newWorksheet = excelHandler.getWorksheet();
	 
	XLSX.utils.book_append_sheet(workBook, XLSX.utils.table_to_sheet(document.getElementById('excelSheet1')), '사용자 이벤트');
	 
	var wbout = XLSX.writeFile(workBook, 'Test.xlsx', {bookType: 'xlsx', type: 'binary'});
	saveAs(new Blob([s2ab(wbout)], {type: 'application/octet-stream'}), '엑셀_다운로드.xlsx');
}