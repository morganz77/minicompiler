	.data
	.align 2
_c:	.space 4
	.data
	.align 2
_d:	.space 4
	.text
_foo:
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 4
	subu  $sp, $sp, 0
	li    $t0, -8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, -12
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	mul   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	b     _foo_Exit
_foo_Exit:
	lw    $ra, 4($fp)
	addu  $t0, $fp, 12
	lw    $fp, 0($fp)
	move  $sp, $t0
	jr    $ra
	.data
	.align 2
_e:	.space 4
	.text
_ftrue:
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 4
	subu  $sp, $sp, 0
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	b     _ftrue_Exit
_ftrue_Exit:
	lw    $ra, 4($fp)
	addu  $t0, $fp, 4
	lw    $fp, 0($fp)
	move  $sp, $t0
	jr    $ra
	.text
_fib:
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 4
	subu  $sp, $sp, 0
	li    $t0, -8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, $t1, .L0
	b     .L3
.L3:
	li    $t0, -8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	beq   $t0, $t1, .L0
	b     .L1
.L0:
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	b     _fib_Exit
	b     .L2
.L1:
	li    $t0, -8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sub   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	jal   _fib
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, -8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 2
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sub   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	jal   _fib
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	add   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	b     _fib_Exit
.L2:
_fib_Exit:
	lw    $ra, 4($fp)
	addu  $t0, $fp, 8
	lw    $fp, 0($fp)
	move  $sp, $t0
	jr    $ra
	.text
_callfib:
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 4
	subu  $sp, $sp, 0
	li    $t0, -8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	jal   _fib
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, -12
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	mul   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $v0, 4($sp)	#POP
	addu  $sp, $sp, 4
	b     _callfib_Exit
_callfib_Exit:
	lw    $ra, 4($fp)
	addu  $t0, $fp, 12
	lw    $fp, 0($fp)
	move  $sp, $t0
	jr    $ra
	.text
	.globl main
main:
__start:
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 4
	subu  $sp, $sp, 12
	li    $t0, 0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of b
	li    $t0, 8
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 22
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 10
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	jal   _foo
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of a
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	neg   $t1, $t1
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L4:	.asciiz	"\n"
	.text
	la    $t0, .L4
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 4
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 30
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	jal   _foo
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of c
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L5:	.asciiz	"\n"
	.text
	la    $t0, .L5
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	seq   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of b2
	li    $t0, 12
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 12
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 1
	bge   $t0, $t1, .L6
	b     .L7
.L6:
	.data
.L8:	.asciiz	"test_!bool\n"
	.text
	la    $t0, .L8
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
.L7:
	li    $t0, 8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	seq   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	beqz  $t0, .L9
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	seq   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L9:
	
#addr gen of b
	li    $t0, 8
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 1
	bge   $t0, $t1, .L10
	b     .L11
.L10:
	.data
.L12:	.asciiz	"test andnode codegen\n"
	.text
	la    $t0, .L12
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
.L11:
	li    $t0, 8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	bnez  $t0, .L15
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	sgt   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
.L15:
	
#addr gen of b
	li    $t0, 8
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 1
	bge   $t1, $t0, .L13
	b     .L14
.L13:
	.data
.L16:	.asciiz	"test\n"
	.text
	la    $t0, .L16
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
.L14:
	jal   _ftrue
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	beq   $t0, $t1, .L17
	b     .L19
.L19:
	b     .L17
.L17:
	.data
.L20:	.asciiz	"test_ftrue\n"
	.text
	la    $t0, .L20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
.L18:
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	seq   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of b
	li    $t0, 8
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 2
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 3
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	seq   $t0, $t0, $t1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of b2
	li    $t0, 12
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 12
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 1
	bge   $t0, $t1, .L23
	b     .L22
.L23:
	li    $t0, 8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 1
	bge   $t0, $t1, .L21
	b     .L22
.L21:
	.data
.L24:	.asciiz	"test_equals\n"
	.text
	la    $t0, .L24
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
.L22:
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of b
	li    $t0, 8
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of b2
	li    $t0, 12
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 12
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 1
	bge   $t0, $t1, .L27
	b     .L26
.L27:
	li    $t0, 8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 1
	bge   $t0, $t1, .L25
	b     .L26
.L25:
	.data
.L28:	.asciiz	"test_str_equals\n"
	.text
	la    $t0, .L28
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
.L26:
	li    $t0, 0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of b
	li    $t0, 8
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of b2
	li    $t0, 12
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t0, 12
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 1
	bge   $t0, $t1, .L31
	b     .L30
.L31:
	li    $t0, 8
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	beq   $t0, $t1, .L29
	b     .L30
.L29:
	.data
.L32:	.asciiz	"test_str_!equals\n"
	.text
	la    $t0, .L32
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
.L30:
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L33:	.asciiz	" "
	.text
	la    $t0, .L33
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of a
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	addi  $t1, $t1, 1
	sw    $t1, 0($t0)
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L34:	.asciiz	"\n"
	.text
	la    $t0, .L34
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L35:	.asciiz	" "
	.text
	la    $t0, .L35
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of a
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	addi  $t1, $t1, -1
	sw    $t1, 0($t0)
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L36:	.asciiz	"\n"
	.text
	la    $t0, .L36
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 12
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $t1, 0
	beq   $t0, $t1, .L37
	b     .L38
.L37:
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L40:	.asciiz	" "
	.text
	la    $t0, .L40
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of a
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	addi  $t1, $t1, 1
	sw    $t1, 0($t0)
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L41:	.asciiz	"\n"
	.text
	la    $t0, .L41
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	b     .L39
.L38:
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L42:	.asciiz	" "
	.text
	la    $t0, .L42
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of a
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	addi  $t1, $t1, -1
	sw    $t1, 0($t0)
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L43:	.asciiz	"\n"
	.text
	la    $t0, .L43
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
.L39:
.L45:
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 200
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	blt   $t0, $t1, .L44
	b     .L46
.L44:
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of a
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	addi  $t1, $t1, 1
	sw    $t1, 0($t0)
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of d
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	b     .L45
.L46:
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L47:	.asciiz	" "
	.text
	la    $t0, .L47
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of a
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	addi  $t1, $t1, -1
	sw    $t1, 0($t0)
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L48:	.asciiz	"\n"
	.text
	la    $t0, .L48
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 1
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of a
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	sw    $t1, 0($t0)
	sw    $t1, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
.L50:
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 20
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	blt   $t0, $t1, .L49
	b     .L51
.L49:
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	jal   _fib
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L52:	.asciiz	"\n"
	.text
	la    $t0, .L52
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
	li    $t0, 4
	sub   $t0, $fp, $t0
	lw    $t0, 0($t0)
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	
#addr gen of a
	li    $t0, 4
	sub   $t0, $fp, $t0
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $t0, 4($sp)	#POP
	addu  $sp, $sp, 4
	lw    $t1, 4($sp)	#POP
	addu  $sp, $sp, 4
	addi  $t1, $t1, 1
	sw    $t1, 0($t0)
	b     .L50
.L51:
	li    $t0, 1000
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	li    $t0, 10
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	jal   _callfib
	sw    $v0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 1
	syscall
	.data
.L53:	.asciiz	"\n"
	.text
	la    $t0, .L53
	sw    $t0, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	lw    $a0, 4($sp)	#POP
	addu  $sp, $sp, 4
	li    $v0, 4
	syscall
_main_Exit:
	lw    $ra, 4($fp)
	addu  $t0, $fp, 4
	lw    $fp, 0($fp)
	move  $sp, $t0
	li    $v0, 10
	syscall
