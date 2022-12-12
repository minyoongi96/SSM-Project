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
	
	XLSX.utils.book_append_sheet(workBook, XLSX.utils.table_to_sheet(document.getElementById('excelSheet0')), '결과 보고'); 
	XLSX.utils.book_append_sheet(workBook, XLSX.utils.table_to_sheet(document.getElementById('excelSheet1')), '시청자 이벤트 현황');
	XLSX.utils.book_append_sheet(workBook, XLSX.utils.table_to_sheet(document.getElementById('excelSheet2')), '키워드별 시청자 이벤트 발생 추이');
	 
	var wbout = XLSX.writeFile(workBook, '엑셀 보고서.xlsx', {bookType: 'xlsx', type: 'binary'});
	//saveAs(new Blob([s2ab(wbout)], {type: 'application/octet-stream'}), '엑셀_다운로드.xlsx');
}