#!/bin/bash

CMD_FILE=$( readlink -f "$0" )
CMD_DIR=$( dirname "$CMD_FILE" )
CLASS_SED_PROGRAM="$CMD_DIR/class-to-xml.sed"
MESSAGE_SED_PROGRAM="$CMD_DIR/messages-to-xml.sed"

# Indentação do arquivo XML
INDENT="    "

ROOT_FOLDER="$1"

ENTITY_FOLDER_NAME="domain"
SOURCE_FILES_REGEX=".*/${ENTITY_FOLDER_NAME}/[A-Za-z0-9_ ]+.java"

# Converte cada espaço no início da linha em indentação
indent() {
    sed -e '
:indent
        s/^\(\s*\) /\1{INDENT}/
        t indent
        s/{INDENT}/'"${INDENT}"'/g
        s/^/'"${INDENT}"'/
    '
}


# Busca pasta se não especificada
if [ -z "$ROOT_FOLDER" ]
then
    ROOT_FOLDER="$CMD_DIR"
    while [ ! -d "$ROOT_FOLDER/src/main/java" ]
    do
        if [ -z "$ROOT_FOLDER" ]
        then
            echo Could not find source root. Please specify as first parameter
            exit 1
        fi
        ROOT_FOLDER=$( dirname "$ROOT_FOLDER" )
    done
    ROOT_FOLDER="$ROOT_FOLDER/src/main/java"
fi

closeModuleTag() {
    if [ ! -z "$PREV_PKG_NAME" ]
    then
        echo " </module>"
    fi
}

# Gera início do arquivo XML
echo '<?xml version="1.0"?>'
echo '<class-list root-folder="'"${ROOT_FOLDER}"'">'

(
    # Gera conteudo do arquivo XML
    IFS=$'\n' # Proteção contra nomes de arquivos com espaços
    for FILE_NAME in $( find "${ROOT_FOLDER}" -regex "${SOURCE_FILES_REGEX}" )
    do
        # Extrai nome do pacote do arquivo
        PACKAGE_NAME=$( sed -n -e '
            s/^\s*package\s\+\([A-Za-z0-9_.]\+\)\s*;\s*$/\1/
            t foundPackage
            b
:foundPackage
            p
            q
        ' "$FILE_NAME")

        # Se houve mudança no nome do pacote, altera módulo
        if [ "$PACKAGE_NAME" != "$PREV_PKG_NAME" ]
        then
   
            closeModuleTag

            # Salva nome do pacote anterior
            PREV_PKG_NAME=${PACKAGE_NAME}

            # Extrai elementos do nome do pacote
            MODULE=${PACKAGE_NAME##*.}
            PACKAGE_NAME=${PACKAGE_NAME%.*}
            SUBSYSTEM=${PACKAGE_NAME##*.}
            PACKAGE_NAME=${PACKAGE_NAME%.*}
            SYSTEM=${PACKAGE_NAME##*.}
            ORGANIZATION=${PACKAGE_NAME%.*}

            # Abre tag module com os componentes do pacote nos atributos
            echo " <module name=\"${MODULE}\" subsystem=\"${SUBSYSTEM}\" system=\"${SYSTEM}\" organization=\"${ORGANIZATION}\">"
        fi

        # Gera lista de classes
        sed -f "$CLASS_SED_PROGRAM" "$FILE_NAME"
            
    done
    
    closeModuleTag

    # Gera mensagens
    echo "<message-files>"
    for FILE_NAME in "$CMD_DIR"/messages*.properties
    do
        BASE_NAME=$( basename "$FILE_NAME" )
        echo " <message-file name=\"${BASE_NAME}\">"
        sed -f "$MESSAGE_SED_PROGRAM" "$FILE_NAME"
        echo " </message-file>"
    done
    echo "</message-files>"
) | indent

# Gera final do arquivo XML de lista
echo '</class-list>'
