#!/bin/bash

CMD_FILE=$( readlink -f "$0" )
CMD_DIR=$( dirname "$CMD_FILE" )
SED_PROGRAM="$CMD_DIR/class-to-xml.sed"

# Indentação do arquivo XML
INDENT="    "

ROOT_FOLDER="$1"

ENTITY_FOLDER_NAME="domain"
SOURCE_FILES_REGEX=".*/${ENTITY_FOLDER_NAME}/[A-Za-z0-9_ ]+.java"

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

(
    closeModuleTag() {
        if [ ! -z "$PREV_PKG_NAME" ]
        then
            echo "    </module>"
        fi
    }

    # Gera início do arquivo XML
    echo '<?xml version="1.0"?>'
    echo '<class-list root-folder="'"${ROOT_FOLDER}"'">'

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
            echo "${INDENT}<module name=\"${MODULE}\" subsystem=\"${SUBSYSTEM}\" system=\"${SYSTEM}\" organization=\"${ORGANIZATION}\">"
        fi

        # Gera lista de classes
        sed -f "$SED_PROGRAM" "$FILE_NAME" | sed -e '
:indent
            s/^\s* /{INDENT}/
            t indent
            s/{INDENT}/'"${INDENT}"'/g
            s/^/'"${INDENT}"'/
        '
            
    done
    
    closeModuleTag

    # Gera final do arquivo XML de lista
    echo '</class-list>'
)
