
# >>> conda initialize >>>
# !! Contents within this block are managed by 'conda init' !!
__conda_setup="$('/Users/a0p07mo/anaconda3/bin/conda' 'shell.zsh' 'hook' 2> /dev/null)"
if [ $? -eq 0 ]; then
    eval "$__conda_setup"
else
    if [ -f "/Users/a0p07mo/anaconda3/etc/profile.d/conda.sh" ]; then
        . "/Users/a0p07mo/anaconda3/etc/profile.d/conda.sh"
    else
        export PATH="/Users/a0p07mo/anaconda3/bin:$PATH"
    fi
fi
unset __conda_setup
# <<< conda initialize <<<

#sledge:binary path
export SLEDGE_BIN=/Users/a0p07mo/.sledge/bin
export PATH="${PATH}:${SLEDGE_BIN}"

# Set JAVA_HOME to JDK 17 explicitly
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH

# Load Angular CLI autocompletion.
source <(ng completion script)
