
// Below is the fix for menu bar. The contents are not hiding on mouse out
//************************************************************************************************
	PrimeFaces.widget.Menubar.prototype.deactivate = function(b, a) {
	       var menu = this;
	       menu.activeitem = null;
	       b.children("a.ui-menuitem-link").removeClass(
	                    "ui-state-hover ui-state-active");
	       b.removeClass("ui-menuitem-active ui-menuitem-highlight");
	       if (a) {
	             b.children("ul.ui-menu-child").fadeOut("fast");
	       } else {
	             b.children("ul.ui-menu-child").hide();
	             if (!this.timer) {
	                    this.timer = setTimeout(function() {
	                           menu.reset();
	                    }, 100);
	             }
	       }
	}
	
	PrimeFaces.widget.Menubar.prototype.reactivate = function(d) {
	       if (this.timer) {
	             clearTimeout(this.timer);
	             this.timer = null;
	       }
	       this.activeitem = d;
	       var c = d.children("ul.ui-menu-child"), b = c
	                    .children("li.ui-menuitem-active:first"), a = this;
	       if (b.length == 1) {
	             a.deactivate(b)
	       }
	}
	
	PrimeFaces.widget.Menubar.prototype.activate = function(b) {
	       if (this.timer) {
	             clearTimeout(this.timer);
	             this.timer = null;
	       }
	       this.highlight(b);
	       var a = b.children("ul.ui-menu-child");
	       if (a.length == 1) {
	             this.showSubmenu(b, a)
	       }
	}
//*****************************************************************************************