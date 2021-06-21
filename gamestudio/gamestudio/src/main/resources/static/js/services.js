function comment(event,player){
    event.preventDefault();

    const comm=document.getElementById("commContent").value.trim();
    const link="/tentrix-minova/addCom?player="+player+"&comment="+comm;

    window.open(link,"_top");

}

function rating(event,player){
    event.preventDefault();

    const input = document.getElementById("form").elements;
    const rating = input["rate"].value.trim();

    const link="/tentrix-minova/addRat?player="+player+"&rating="+rating;
    window.open(link,"_top");
}