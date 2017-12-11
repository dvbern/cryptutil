#!/bin/bash

function sign {
	infile="${1}"
	algoparam="${2}"
	digestfile="${3}"
	pkfile="${4}"
	pkpass="${5}"

	openssl dgst "${algoparam}" -sign "${pkfile}" -out "${digestfile}" -passin "pass:${pkpass}" "${infile}"
}

sign ../../test-input.jpg -sha256 sha256.dsig ../testkey-nopass.pem ""
sign ../../test-input.jpg -sha256 sha256-passasdffdsa.dsig ../testkey-passasdffdsa.pem "asdffdsa"

sign ../../test-input.jpg -sha512 sha512.dsig ../testkey-nopass.pem ""
sign ../../test-input.jpg -sha512 sha512-passasdffdsa.dsig ../testkey-passasdffdsa.pem "asdffdsa"
