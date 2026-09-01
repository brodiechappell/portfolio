(function(){
    function q(sel, root=document){return root.querySelector(sel)}
    function qa(sel, root=document){return Array.from(root.querySelectorAll(sel))}

    //Pagination
    function Pager(containerId, perPage){
        this.container = document.getElementById(containerId)
        this.perPage = perPage || 6
        this.current = 1
        this.tours = window.__TOURS__ || []
        this.totalPages = Math.max(1, Math.ceil(this.tours.length / this.perPage))
    }
    Pager.prototype.render = function(){
        const start=(this.current-1)*this.perPage
        const pageArray=this.tours.slice(start,start+this.perPage)
        this.container.innerHTML=''
        pageArray.forEach(tour=>{
            const card=document.createElement('div')
            card.className='card'
            card.innerHTML=`
                ${tour.imageLink?`<img src="${tour.imageLink}" alt="${tour.tourTitle}">`:''}
                <h3>${tour.tourTitle}</h3>
                <p class="meta">${tour.short_desc||''}</p>
                <p class="price">£${Number(tour.price).toFixed(2)}</p>
                <p><a href="tour.php?tourID=${tour.tourID}" class="btn">View Full Details</a></p>
                <form class="add-to-cart" data-tourid="${tour.tourID}">
                  <input type="number" name="groupSize" value="1" min="1">
                  <button type="submit">Add to Cart</button>
                </form>
            `
            this.container.appendChild(card)
        })
        const pag = document.getElementById('paginationControls')
        if(pag){
            pag.innerHTML=`<button id="prevPage">Prev</button> <span>Page ${this.current} / ${this.totalPages}</span> <button id="nextPage">Next</button>`
            q('#prevPage',pag).disabled=this.current===1
            q('#nextPage',pag).disabled=this.current===this.totalPages
            q('#prevPage',pag).onclick=()=>{this.current=Math.max(1,this.current-1);this.render()}
            q('#nextPage',pag).onclick=()=>{this.current=Math.min(this.totalPages,this.current+1);this.render()}
        }
    }

    function setupAddToCart(containerId){
        const container=document.getElementById(containerId)
        if(!container)return
        container.addEventListener('submit',function(e){
            const form=e.target.closest('form.add-to-cart')
            if(!form)return
            e.preventDefault()
            const groupSize=parseInt(form.groupSize.value,10)
            if(Number.isNaN(groupSize)||groupSize<1){alert('Invalid group size');return}
            form.submit()
        })
    }

    function updateCartCounter(){
        const el=q('#cartCounter')
        if(!el)return
        const cart=window.__CART__||{}
        el.textContent=Object.keys(cart).length
    }

    window.Site={Pager,setupAddToCart,updateCartCounter}
})();
