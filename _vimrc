set number
call plug#begin('~/vimfiles/plugged')

" Shorthand notation; fetches https://github.com/junegunn/vim-easy-align
Plug 'Raimondi/delimitMate'
Plug 'gioele/vim-autoswap'
Plug 'majutsushi/tagbar'
Plug 'SirVer/ultisnips' | Plug 'honza/vim-snippets'
Plug 'Shougo/unite.vim'
Plug 'tpope/vim-fugitive'
Plug 'tpope/vim-commentary'
Plug 'neowit/vim-force.com'
Plug 'tpope/vim-unimpaired'
Plug 'tpope/vim-sensible'
Plug 'tpope/vim-scriptease'
Plug 'Shougo/vimproc.vim'
Plug 'tpope/vim-surround'
Plug 'tpope/vim-markdown'
Plug 'flazz/vim-colorschemes'
Plug 'airblade/vim-rooter'
Plug 'scrooloose/syntastic'
Plug 'vim-airline/vim-airline'
Plug 'vim-airline/vim-airline-themes'
Plug 'mattn/emmet-vim'
Plug 'leafgarland/typescript-vim'
Plug 'tpope/vim-surround'
Plug 'vim-syntastic/syntastic'
Plug 'fatih/vim-go'


" for chinese windows
set encoding=utf-8
set termencoding=utf-8
set fileencoding=utf-8
set fileencodings=ucs-bom,utf-8,chinese,cp936
language messages zh_CN.utf-8

call plug#end()

syntax on
filetype plugin indent on
map <F1> <Esc>
imap <F1> <Esc>
color desert

set nocompatible
set hidden
 
let g:syntastic_always_populate_loc_list = 1
let g:syntastic_auto_loc_list = 1
let g:syntastic_check_on_open = 1
let g:syntastic_check_on_wq = 0
let g:syntastic_javascript_checkers = ['eslint']

let g:airline#extensions#branch#enabled = 1
let g:airline#extensions#branch#empty_message = ''
let g:airline#extensions#branch#vcs_priority = ["git", "mercurial"]
let g:airline#extensions#syntastic#enabled = 1
let g:airline#extensions#tabline#enabled = 1
let g:airline#extensions#unitv#enabled = 1
let g:airline#extensions#tagbar#enabled = 1
set ttimeoutlen=50
let g:airline_theme = 'powerlineish'
let g:airline#extensions#hunks#enabled=0
if !exists('g:airline_symbols')
  let g:airline_symbols = {}
endif
let g:airline_symbols.space = "\ua0"

let g:apex_tooling_force_dot_com_path = "/home/tony/vim_force/tooling-force.com-0.3.3.2.jar"
if !exists("g:apex_backup_folder")
	" full path required here, relative may not work
	let g:apex_backup_folder="/home/tony/vim_force/backup"
endif
if !exists("g:apex_temp_folder")
	" full path required here, relative may not work
	let g:apex_temp_folder="/home/tony/vim_force/gvim-deployment"
endif
if !exists("g:apex_properties_folder")
	" full path required here, relative may not work
	let g:apex_properties_folder="/home/tony/vim_force/secure-properties"
endif
let g:apex_workspace_path = "/home/tony/vim_force/workspace"
let g:apex_ctags_cmd = "/usr/bin/ctags" 

let g:apex_server=1 " start server on first call
let g:apex_server_timeoutSec=60*30
let g:apex_server_port = 65000
let g:apex_diff_cmd="vimdiff"


" set foldmethod=syntax
" set foldlevelstart=1

" let javaScript_fold=1         " JavaScript
" let perl_fold=1               " Perl
" let php_folding=1             " PHP
" let r_syntax_folding=1        " R
" let ruby_fold=1               " Ruby
" let sh_fold_enabled=1         " sh
" let vimsyn_folding='af'       " Vim script
" let xml_syntax_folding=1      " XML

nnoremap <C-F2> :TagbarToggle<CR>
nnoremap <leader>js :set filetype=javascript<cr>
nnoremap <leader>css :set filetype=css<cr>
nnoremap <leader>page :set filetype=visualforce<cr>
nnoremap <leader>cls :set filetype=apexcode<cr>
nnoremap <leader>html :set filetype=html<cr>
nnoremap <leader>xml :set filetype=xml<cr>
nnoremap <leader>ev :e ~/_vimrc<cr>
nnoremap <leader>sv :source ~/_vimrc<cr>
" nnoremap <leader>f :<C-u>Unite -start-insert file<CR>
" nnoremap <leader>b :<C-u>Unite -start-insert buffer<CR>
nnoremap <leader>f :<C-u>Unite file<CR>
nnoremap <leader>b :<C-u>Unite buffer<CR>
nnoremap <leader>at :<C-u>ApexTest testAndDeploy <C-R>=expand("%:t:r")<CR><CR>
nnoremap <leader>al :<C-u>ApexLog<CR>
nnoremap <leader>ae :<C-u>ApexExecuteAnonymous<CR>
nnoremap <leader>asv :<C-u>ApexSave<CR>
nnoremap <leader>ast :<C-u>ApexStage<CR>
nnoremap <leader>asa :<C-u>ApexStageAdd<CR>
nnoremap <leader>asr :<C-u>ApexStageRemove<CR>
nnoremap <leader>asc :<C-u>ApexStageClear<CR>
nnoremap <leader>ac :<C-u>ApexScratch<CR>
nnoremap <leader>aq :<C-u>ApexQuery<CR>
inoremap <M-;> <Esc>A;

 function! s:setApexShortcuts()

	""""""""""""""""""""""""""""""""""""""""""
	" Search in files
	""""""""""""""""""""""""""""""""""""""""""

	" search exact word
	nmap <leader>sc :noautocmd vimgrep /\<<C-R><C-W>\>/j ../**/*.cls ../**/*.trigger <CR>:cwin<CR>
	nmap <leader>st :noautocmd vimgrep /\<<C-R><C-W>\>/j ../**/*.trigger <CR>:cwin<CR>
	nmap <leader>sp :noautocmd vimgrep /\<<C-R><C-W>\>/j ../**/*.page <CR>:cwin<CR>
	nmap <leader>ss :noautocmd vimgrep /\<<C-R><C-W>\>/j ../**/*.scf <CR>:cwin<CR>
	nmap <leader>sa :noautocmd vimgrep /\<<C-R><C-W>\>/j ../**/*.cls ../**/*.trigger ../**/*.page ../**/*.scf <CR>:cwin<CR>

	" search - *contains* - partal match is allowed
	nmap <leader>sC :noautocmd vimgrep /<C-R><C-W>/j ../**/*.cls ../**/*.trigger <CR>:cwin<CR>
	nmap <leader>sT :noautocmd vimgrep /<C-R><C-W>/j ../**/*.trigger <CR>:cwin<CR>
	nmap <leader>sP :noautocmd vimgrep /<C-R><C-W>/j ../**/*.page <CR>:cwin<CR>
	nmap <leader>sS :noautocmd vimgrep /<C-R><C-W>/j ../**/*.scf <CR>:cwin<CR>
	nmap <leader>sA :noautocmd vimgrep /<C-R><C-W>/j ../**/*.cls ../**/*.trigger ../**/*.page ../**/*.scf <CR>:cwin<CR>

	" prepare search string, but do not run
	nmap <leader>sm :noautocmd vimgrep /\<<C-R><C-W>\>/j ../**/*.cls ../**/*.trigger ../**/*.page ../**/*.scf \|cwin

	" search visual selection in Apex project
	function! ApexFindVisualSelection(searchType) range
		let l:apex_search_patterns = {'class': '../**/*.cls ../**/*.trigger', 
										\'trigger': '../**/*.trigger', 
										\'page': '../**/*.page', 
										\'all': '../**/*.cls ../**/*.trigger ../**/*.page ../**/*.scf'}
		let l:saved_reg = @"
		execute "normal! vgvy"

		let l:pattern = escape(@", '\\/.*$^~[]')
		let l:pattern = substitute(l:pattern, "\n$", "", "")

		let commandLine="noautocmd vimgrep " . '/'. l:pattern . '/j '

		let commandLine = commandLine . l:apex_search_patterns[a:searchType]
		"echo "commandLine=" . commandLine
		execute commandLine 
		execute 'cwin'

		let @/ = l:pattern
		let @" = l:saved_reg
	endfunction
	vmap <leader>sc :call ApexFindVisualSelection('class')<CR>
	vmap <leader>st :call ApexFindVisualSelection('trigger')<CR>
	vmap <leader>sp :call ApexFindVisualSelection('page')<CR>
	vmap <leader>sa :call ApexFindVisualSelection('all')<CR>
 
 
 	""""""""""""""""""""""""""""""""""""""""""
 	" CTags shortcuts
 	""""""""""""""""""""""""""""""""""""""""""
 	" shortcut to update ctags DB manually
 	" note for XFCE: disable default workspace 11 switch (Ctrl-F11) shortcut
 	" (settings-> Window Manager -> Keyboard),
 	" otherwise C-F11 in vim does not work
 	map <C-F1> <Esc>:ApexUpdateCtags<CR>
 
 endfunction
 
 " load shortcut mapping when one of apexcode file types is detected/loaded
 autocmd FileType apexcode.java call s:setApexShortcuts()
 autocmd FileType apexcode.html call s:setApexShortcuts()
 autocmd FileType apexcode.javascript call s:setApexShortcuts()
 autocmd FileType apexcode.html call s:setApexShortcuts()
 autocmd BufReadPost fugitive://* set bufhidden=delete

call s:setApexShortcuts()
set tabstop=8     " Set the default tabstop
set softtabstop=4
set shiftwidth=4  " Set the default shift width for indents
set smarttab      " Smarter tab levels
set expandtab
