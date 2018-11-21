    /@Entity/,$ {
        s#\s*public\s\+class\s\+\([A-Za-z0-9_]\+\)\s\+.*{\s*$#<class name="\1">#
        t print
        /^\s*@/ {
            /@NotNull/ {
                s/.*/nullable="false"/
                H
                d
            }
        }
        s/\s*private\s\+\([A-Za-z0-9_]\+\)\s\+\([A-Za-z0-9_]\+\)\s*;\s*$/ <field name="\2" type="\1"/
        t field
        b continue
:field
        G
        s/\n/ /g
        s#\s*$#/>#
        h
        s/.*//
        x
        b print
:continue
        $ {
            s#.*#</class>#
            t print
            b next
        }
        b next
:print
        p
    }
:next
    d
