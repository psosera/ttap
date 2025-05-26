# Blocky Fixes

## engine.BlockyGame

+   Bag-shuffling code added (‡).
    -   trySpawnBlock:58: called bag-shuffling code to spawn blocks.
+   processMovement:67: fixed null pointer when pieces lock by adding a null 
    check for activePiece.
+   processMovement:77: fixed IllegalStateException thrown when passing right 
    by adding a break statement.
+   step:112: added missing call to processMovement to actually process
    movement.
+   rotatePiece:123*: (*) added collision check on rotating pieces to prevent
    clipping while rotating.

## engine.Board

+   isValidPosition:17: fixed array OOB error by making col upper-bound check
    exclusive.
+   collides:28 and addToWell:47: (*) addressed coordinate system bug between
    active piece and well by fixing how wellRow is computed from position and
    active piece layout.
+   deleteRow:60, deleteRow:66, and deleteRow:89: (*) fixed row deletion to
    behave Tetris-like. The lines above the deleted ones should be shifted
    downwards into the gap created by the cleared lines. The fix involves
    changing the order in which we copy rows down.
+   deleteRows:92: fixed typecast error arising from putting an incorrect
    type into the completedRows list. The list should be a list of row indices,
    but the original code added a PieceKind.

## gfx.BlockyPanel

+   BlockyPanel:24: (*) fixed off-by-one in height calculation causing the board
    to not render on the bottom-most row of the screen.
+   BlockyPanel:43 and BlockyPanel:55: (*) fixed inconsistent rendering with the
    panel and grid's coordinate systems. The fix to BlockyPanel:55 also
    addresses rendering the well off the bottom of the screen.

## util.Loader

+   readRotation:15: fixed OOB error when loading in data files. The bounds for
    columns was off-by-one.