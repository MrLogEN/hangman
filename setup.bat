@echo off

SET PRE_COMMIT_SOURCE=config\.hooks\pre-commit
SET PRE_COMMIT_TARGET=.git\hooks\pre-commit

IF EXIST "%PRE_COMMIT_TARGET%" (
	echo Removing existing file at %PRE_COMMIT_TARGET%
	del "%PRE_COMMIT_TARGET%"	
)

echo Copying pre-commit hook from %PRE_COMMIT_SOURCE% to %PRE_COMMIT_TARGET%
copy "%PRE_COMMIT_SOURCE%" "%PRE_COMMIT_TARGET%"
