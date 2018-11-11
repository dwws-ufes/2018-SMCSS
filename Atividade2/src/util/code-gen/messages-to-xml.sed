/^#\s*<for-each/, /^#\s*<\/for-each>/ {
    s_^#\s*\(<\/\?for-each.*>\).*_\1_
    t print
    s#ENTITY_NAME#<entity-name/>#g
    s#CLASS_NAME#<class-name/>#g
    s#FIELD_NAME#<field-name/>#g
    s#SUBSYSTEM#<subsystem-name/>#g
    s#^# <text-line>#
    s#$#</text-line>#
    b print
}
d
:print
    s/^/  /
