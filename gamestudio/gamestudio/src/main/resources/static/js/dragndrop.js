let idx;
function onDragStart(event,index) {
    idx=index;

}
function onDragOver(event) {
  event.preventDefault();
}

function onDrop(event,row,column) {
event.preventDefault();
  const link="/tentrix-minova?index="+idx+"&row="+row+"&column="+column;

  window.open(link,"_top");
}