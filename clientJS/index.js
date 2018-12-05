$( function() {
  var availableTags = [
    "ActionScript",
    "AppleScript",
    "Asp",
    "BASIC",
    "C",
    "C++",
    "Clojure",
    "COBOL",
    "ColdFusion",
    "Erlang",
    "Fortran",
    "Groovy",
    "Haskell",
    "Java",
    "JavaScript",
    "Lisp",
    "Perl",
    "PHP",
    "Python",
    "Ruby",
    "Scala",
    "Scheme"
  ];
  /*$( "#searchBox" ).autocomplete({
          //source: availableTags
          source: availableTags,
          search: function () {
            console.log(this.value);
            var term = extractLast(this.value);
            console.log('term: ' + term);
            if (term.length < 1) {
              return false;
            }
            return term;
          },
          // prevent value inserted on focus
          focus: function () {
            return false;
          },
          select: function (event, ui) {
            var terms = split(this.value);
            // remove the current input
            terms.pop();
            // add the selected item
            terms.push(ui.item.value);
            // add placeholder to get the comma-and-space at the end
            terms.push("");
            this.value = terms.join(", ");
            return false;
          }
        });*/
  $( "#searchBox" )
  // don't navigate away from the field on tab when selecting an item
    .on( "keydown", function( event ) {
    if ( event.keyCode === $.ui.keyCode.TAB &&
        $( this ).autocomplete( "instance" ).menu.active ) {
      event.preventDefault();
    }
  })
    .autocomplete({
    minLength: 0,
    source: function( request, response ) {
      // delegate back to autocomplete, but extract the last term
      response( $.ui.autocomplete.filter(
        availableTags, extractLast( request.term ) ) );
    },
    focus: function() {
      // prevent value inserted on focus
      return false;
    },
    select: function( event, ui ) {
      var terms = split( this.value );
      // remove the current input
      terms.pop();
      // add the selected item
      terms.push( ui.item.value );
      // add placeholder to get the comma-and-space at the end
      terms.push( "" );
      this.value = terms.join( ", " );
      return false;
    }
  })
  function split(val) {
    //return val.split(/,\s*/);
    return val.split(',');
  }
  function extractLast(term) {
    return split(term).pop().trim();
  };
})